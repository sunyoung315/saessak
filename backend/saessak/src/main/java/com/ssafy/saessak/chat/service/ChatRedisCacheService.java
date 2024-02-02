package com.ssafy.saessak.chat.service;

import com.ssafy.saessak.chat.domain.Chat;
import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.chat.domain.Visit;
import com.ssafy.saessak.chat.dto.ChatMessage;
import com.ssafy.saessak.chat.dto.RoomResponseDto;
import com.ssafy.saessak.chat.repository.ChatRepository;
import com.ssafy.saessak.chat.repository.RoomRepository;
import com.ssafy.saessak.chat.repository.VisitRepository;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.ParentRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import com.ssafy.saessak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRedisCacheService {
    public static final String NEW_CHAT = "NEW_CHAT";
    public static final String CHAT_SORTED_SET_ = "CHAT_SORTED_SET_";
    //  채팅 내역과 채팅방에 해당하는 채팅 내역
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, ChatMessage> chatRedisTemplate;
    private ZSetOperations<String, ChatMessage> zSetOperations;
    @PostConstruct
    private void init() {
        zSetOperations = chatRedisTemplate.opsForZSet();
    }

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;

    // Redis의 Chatting data caching 처리
    // 채팅 등록
    public void addChat(ChatMessage message) {
        // 입력용 -> 한 시간 마다 DB INSERT 시킬 용도
        redisTemplate.opsForZSet()
                .add(
                        NEW_CHAT,
                        message,
                        changeLocalDateTimeToDouble(message.getChatTime()));

        // 조회용 -> redis에 존재하면 그 채팅부터 조회하도록 하기 위함
        redisTemplate.opsForZSet().add(CHAT_SORTED_SET_ + message.getRoomId(), message, changeLocalDateTimeToDouble(message.getChatTime()));
    }

    public Double changeLocalDateTimeToDouble(String createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(createdAt, formatter);
        return ((Long) localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).doubleValue();
    }


    // 채팅방 정보 조회 + 마지막 채팅 읽었는지 여부 판단
    public RoomResponseDto roomInfoToDto(Long userId, Room room) {

        User user = userRepository.findById(userId).get();
        Visit visit = visitRepository.findByUserAndRoom(user, room);

        // 마지막 chat 가져오기
        ChatMessage recentChatMessage = null;
        Long size = zSetOperations.size(CHAT_SORTED_SET_ + room.getRoomId()); // Caching 데이터에서 가장 마지막 값
        if(size == 0){
            Chat chat = chatRepository.findFirstByRoomOrderByChatTimeDesc(room); // 아니라면 db에서 찾아오기
            if(chat != null){
                recentChatMessage = ChatMessage.builder()
                        .chatContent(chat.getChatContent())
                        .chatTime(chat.getChatTime())
                        .build();
            }

        } else {
            recentChatMessage = zSetOperations.range(CHAT_SORTED_SET_ + room.getRoomId(), size - 1, size).iterator().next();
        }

        if(recentChatMessage == null){
            return RoomResponseDto.builder()
                    .roomId(room.getRoomId())
                    .kidId(room.getKid().getId())
                    .kidName(room.getKid().getNickname())
                    .teacherName(room.getTeacher().getNickname())
                    .flag(false)
                    .build();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(recentChatMessage.getChatTime(), formatter);

        boolean isChatTimeBeforeLastVisitTime = localDateTime.isAfter(visit.getVisitTime());
        return RoomResponseDto.builder()
                .roomId(room.getRoomId())
                .kidId(room.getKid().getId())
                .kidName(room.getKid().getNickname())
                .teacherName(room.getTeacher().getNickname())
                .lastChat(recentChatMessage.getChatContent())
                .flag(isChatTimeBeforeLastVisitTime)
                .build();
    }

}
