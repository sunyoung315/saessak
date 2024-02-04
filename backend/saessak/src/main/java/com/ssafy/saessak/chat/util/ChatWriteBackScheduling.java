package com.ssafy.saessak.chat.util;

import com.ssafy.saessak.chat.domain.Chat;
import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.chat.dto.ChatMessage;
import com.ssafy.saessak.chat.repository.ChatJdbcRepository;
import com.ssafy.saessak.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatWriteBackScheduling {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, ChatMessage> chatRedisTemplate;


    private final ChatJdbcRepository chatJdbcRepository;
    private final RoomRepository roomRepository;


    //  매일 1?시부터 1시간마다 Redis Cache에 있는 채팅 데이터를 MySql에 저장
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void writeBack(){
        log.info("[ChatWriteBackScheduling writeBack] Scheduling start");
        //  여기서부터 읽어오는 과정.
        BoundZSetOperations<String, ChatMessage> setOperations = chatRedisTemplate.boundZSetOps("NEW_CHAT");

        ScanOptions scanOptions = ScanOptions.scanOptions().build();

        List<Chat> chatList = new ArrayList<>();
        try (Cursor<ZSetOperations.TypedTuple<ChatMessage>> cursor= setOperations.scan(scanOptions)){

            while (cursor.hasNext()){
                ZSetOperations.TypedTuple<ChatMessage> chatMessage = cursor.next();

                Room chatroom = roomRepository
                        .findById(chatMessage.getValue().getRoomId())
                        .orElse(null);

                if(chatroom == null) {
                    continue;
                }

                Chat chat = Chat.builder()
                        .chatContent(chatMessage.getValue().getChatContent())
                        .senderId(chatMessage.getValue().getSenderId())
                        .chatTime(chatMessage.getValue().getChatTime())
                        .room(chatroom)
                        .build();
                chatList.add(chat);
            }
            chatJdbcRepository.batchInsertChats(chatList);

            redisTemplate.delete("NEW_CHAT");

        }  catch (Exception e){
            e.printStackTrace();
        }

        log.info("Scheduling done");
    }

}