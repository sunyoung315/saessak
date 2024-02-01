package com.ssafy.saessak.chat.service;

import com.ssafy.saessak.chat.domain.Chat;
import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.chat.dto.ChatMessage;
import com.ssafy.saessak.chat.dto.ChatMessageResponse;
import com.ssafy.saessak.chat.dto.RoomResponseDto;
import com.ssafy.saessak.chat.repository.ChatRepository;
import com.ssafy.saessak.chat.repository.RoomRepository;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.user.domain.*;
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

    private final AuthenticationService authenticationService;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;
    private final KidRepository kidRepository;
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;

    public List<RoomResponseDto> getRoomByParent() {
        User user = authenticationService.getUserByAuthentication();
        Parent parent = parentRepository.findById(user.getId()).get();
        List<Room> roomList = new ArrayList<>();
        for(Kid k : parent.getKidList()){
            List<Room> tmpList = roomRepository.findAllByKid(k);
            roomList.addAll(tmpList);
        }
        return getRoomResponseDtos(roomList);
    }

    public List<RoomResponseDto> getRoomByTeacher() {
        User user = authenticationService.getUserByAuthentication();
        Teacher teacher = teacherRepository.findById(user.getId()).get();
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

        //boolean isChatTimeBeforeLastVisitTime = chat.getChatTime().isBefore(r.getLastVisitTime());
        boolean isChatTimeBeforeLastVisitTime = false;
        return RoomResponseDto.builder()
                .roomId(r.getRoomId())
                .kidId(r.getKid().getId())
                .kidName(r.getKid().getNickname())
                .lastChat(chat.getChatContent())
                .flag(isChatTimeBeforeLastVisitTime)
                .build();
    }

    // 채팅방 학부모 생성
    public Long addParentRoom(Long teacherId) {

        User user = authenticationService.getUserByAuthentication(); // 학부모
        Teacher teacher = teacherRepository.findById(teacherId).get();

        List<Kid> kidList1 = kidRepository.findAllByParent((Parent) user);
        List<Kid> kidList2 = kidRepository.findAllByClassroom(teacher.getClassroom());

        Kid kid = null;
        for (Kid k : kidList2) {
            if (kidList1.contains(k)) {
                kid = k;
            }
        }

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


    // 채팅방 선생님 생성
    public Long addTeacherRoom(Long kidId) {
        User user = authenticationService.getUserByAuthentication(); //선생님

        Teacher teacher = teacherRepository.findById(user.getId()).get();
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

    public Long isValid() {
        User user = authenticationService.getUserByAuthentication();
        return user.getId();
    }
}
