package com.ssafy.saessak.chat.service;

import com.ssafy.saessak.chat.dto.ChatMessage;
import com.ssafy.saessak.chat.repository.ChatRepository;
import com.ssafy.saessak.chat.repository.RoomRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import com.ssafy.saessak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

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
    private ZSetOperations<String, ChatMessage> zSetOperations;
    private ChatRepository chatRepository;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

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

}
