package com.ssafy.saessak.chat.service;

import com.ssafy.saessak.chat.domain.Chat;
import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.chat.dto.ChatMessageRequest;
import com.ssafy.saessak.chat.dto.ChatMessageResponse;
import com.ssafy.saessak.chat.dto.RoomResponseDto;
import com.ssafy.saessak.chat.repository.ChatRepository;
import com.ssafy.saessak.chat.repository.RoomRepository;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.domain.Teacher;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;
    private final KidRepository kidRepository;
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;

    public List<RoomResponseDto> getRoomByParent(Long parentId) {
        Parent parent = parentRepository.findById(parentId).get();
        List<Room> roomList = new ArrayList<>();
        for(Kid k : parent.getKidList()){
            List<Room> tmpList = roomRepository.findAllByKid(k);
            roomList.addAll(tmpList);
        }
        return getRoomResponseDtos(roomList);
    }

    public List<RoomResponseDto> getRoomByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        List<Room> roomList = roomRepository.findAllByTeacher(teacher);
        return getRoomResponseDtos(roomList);
    }

    @NotNull
    private List<RoomResponseDto> getRoomResponseDtos(List<Room> roomList) {
        List<RoomResponseDto> roomResponseDtoList = new ArrayList<>();
        for(Room r : roomList){
            RoomResponseDto roomResponseDto = roomInfoToDto(r);
            roomResponseDtoList.add(roomResponseDto);
        }
        return roomResponseDtoList;
    }

    private RoomResponseDto roomInfoToDto(Room r) {
        Chat chat = chatRepository.findFirstByRoomOrderByChatTimeDesc(r);
        if(chat == null){
            return RoomResponseDto.builder()
                    .roomId(r.getRoomId())
                    .kidId(r.getKid().getId())
                    .kidName(r.getKid().getNickname())
                    .flag(false)
                    .build();
        }

        boolean isChatTimeBeforeLastVisitTime = chat.getChatTime().isBefore(r.getLastVisitTime());
        return RoomResponseDto.builder()
                .roomId(r.getRoomId())
                .kidId(r.getKid().getId())
                .kidName(r.getKid().getNickname())
                .lastChat(chat.getChatContent())
                .flag(isChatTimeBeforeLastVisitTime)
                .build();
    }

    // 채팅방 생성
    public Long addRoom(Long teacherId, Long kidId) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        Kid kid = kidRepository.findById(kidId).get();
        Room room = roomRepository.findByKidAndTeacher(kid, teacher);

        if(room == null){
            room = roomRepository.save(Room.builder()
                    .kid(kid)
                    .teacher(teacher)
                    .lastVisitTime(LocalDateTime.now())
                    .build());
        }
        return room.getRoomId();
    }


    // 채팅 저장
    public void saveMessage(ChatMessageRequest message){
        String dateString = message.getChatTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

        Chat chatMessage = Chat.builder()
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .chatContent(message.getChatContent())
                .chatTime(dateTime)
                .room(roomRepository.findById(message.getRoomId()).get())
                .build();
        chatRepository.save(chatMessage);
    }

    public List<ChatMessageResponse> getAllChat(Long roomId) {
        Room room = roomRepository.findById(roomId).get();

        List<ChatMessageResponse> chatMessageResponseList = new ArrayList<>();
        for(Chat c : room.getChatList()){
            ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder()
                    .senderId(c.getSenderId())
                    .receiverId(c.getReceiverId())
                    .chatContent(c.getChatContent())
                    .chatTime(c.getChatTime().toString())
                    .build();
            chatMessageResponseList.add(chatMessageResponse);
        }
        return chatMessageResponseList;
    }
}
