package com.ssafy.saessak.chat.service;

import com.ssafy.saessak.chat.domain.Chat;
import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.chat.domain.Visit;
import com.ssafy.saessak.chat.dto.ChatMessage;
import com.ssafy.saessak.chat.dto.ChatPagingRequestDto;
import com.ssafy.saessak.chat.dto.ChatPagingResponseDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    private final RoomRepository roomRepository;

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

        if(recentChatMessage == null || visit == null){
            return RoomResponseDto.builder()
                    .roomId(room.getRoomId())
                    .kidId(room.getKid().getId())
                    .kidName(room.getKid().getNickname())
                    .teacherName(room.getTeacher().getNickname())
                    .kidProfile(room.getKid().getProfile())
                    .teacherProfile(room.getTeacher().getProfile())
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
                .kidProfile(room.getKid().getProfile())
                .teacherProfile(room.getTeacher().getProfile())
                .flag(isChatTimeBeforeLastVisitTime)
                .build();
    }

    // 채팅방 전체 내역 확인
    public List<ChatPagingResponseDto> getChatsFromRedis(Long roomId, ChatPagingRequestDto chatPagingDto) {

        //마지막 채팅을 기준으로 redis의 Sorted set에 몇번째 항목인지 파악
        ChatMessage cursorDto = ChatMessage.builder()
                .roomId(roomId)
                .senderId(chatPagingDto.getSenderId())
                .chatContent(chatPagingDto.getChatContent())
                .chatTime(chatPagingDto.getChatTime())
                .build();


        //마지막 chat_data cursor Rank 조회
        Long rank =
                zSetOperations.reverseRank(CHAT_SORTED_SET_ + roomId, cursorDto);

        //Cursor 없을 경우 -> 최신채팅 조회
        if (rank == null)
            rank = 0L;
        else rank = rank + 1;

        //Redis 로부터 chat_data 조회
        Set<ChatMessage> chatMessageSaveDtoSet =
                zSetOperations.reverseRange(CHAT_SORTED_SET_ + roomId, rank, rank + 10);


        List<ChatPagingResponseDto> chatMessageDtoList = new ArrayList<>();
        for (ChatMessage chatMessage : chatMessageSaveDtoSet) {
            ChatPagingResponseDto chatPagingResponseDto = ChatPagingResponseDto.builder()
                    .senderId(chatMessage.getSenderId())
                    .chatContent(chatMessage.getChatContent())
                    .roomId(roomId)
                    .chatTime(chatMessage.getChatTime())
                    .build();
            chatMessageDtoList.add(chatPagingResponseDto);
        }

        //Chat_data 부족할경우 MYSQL 추가 조회
        if (chatMessageDtoList.size() != 10) {
            findOtherChatDataInMysql(chatMessageDtoList, roomId, chatPagingDto.getChatTime());
        }
        Collections.reverse(chatMessageDtoList);

        return chatMessageDtoList;
    }


    private void findOtherChatDataInMysql(
            List<ChatPagingResponseDto> chatMessageDtoList,
            Long roomId,
            String cursor) {

        Room room = roomRepository.findById(roomId).get();

        String lastCursor;
        // 데이터가 하나도 없을 경우 현재시간을 Cursor로 활용
        if (chatMessageDtoList.size() == 0 && cursor == null) {
            lastCursor = LocalDateTime
                    .now()
                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS"));
        }

        //redis 적재된 마지막 데이터를 입력했을 경우.
        else if (chatMessageDtoList.size() == 0 && cursor != null) {
            lastCursor = cursor;
        }

        // 데이터가 존재할 경우 CreatedAt을 Cursor로 사용
        else lastCursor = chatMessageDtoList.get(chatMessageDtoList.size() - 1).getChatTime();

        int dtoListSize = chatMessageDtoList.size();
        Slice<Chat> chatSlice =
                chatRepository
                        .findAllByChatTimeBeforeAndRoomOrderByChatTimeDesc(
                                lastCursor,
                                room,
                                PageRequest.of(0, 30)
                        );

        for (Chat chat : chatSlice.getContent()) {
            cachingDBDataToRedis(chat); // 추가 데이터를 가져와서 그걸 redis로 다시 올려준다.
        }


        //추가 데이터가 없을 때 return;
        if (chatSlice.getContent().isEmpty())
            return;

        //추가 데이터가 존재하다면, responseDto에  데이터 추가.
        for (int i = dtoListSize; i <= 10; i++) {
            try {
                Chat chat = chatSlice.getContent().get(i - dtoListSize);
                chatMessageDtoList.add(ChatPagingResponseDto.builder()
                        .senderId(chat.getSenderId())
                        .chatContent(chat.getChatContent())
                        .roomId(chat.getRoom().getRoomId())
                        .chatTime(chat.getChatTime())
                        .build());
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }

    public void cachingDBDataToRedis(Chat chat) {
        ChatMessage chatMessageSaveDto = ChatMessage.builder()
                .chatTime(chat.getChatTime())
                .roomId(chat.getRoom().getRoomId())
                .chatContent(chat.getChatContent())
                .senderId(chat.getSenderId())
                .build();

        redisTemplate.opsForZSet()
                .add(
                        CHAT_SORTED_SET_ + chatMessageSaveDto.getRoomId(),
                        chatMessageSaveDto,
                        changeLocalDateTimeToDouble(chatMessageSaveDto.getChatTime()));
    }
}
