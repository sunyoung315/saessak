package com.ssafy.saessak.chat.service;

import com.ssafy.saessak.chat.domain.Chat;
import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.chat.dto.ChatMessageRequest;
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
//        Chat chat = chatRepository.findFirstByRoomOrderByChatTimeDesc(r);
//
//        if(chat == null){
//            return RoomResponseDto.builder()
//                    .roomId(r.getRoomId())
//                    .kidId(r.getKid().getKidId())
//                    .kidName(r.getKid().getKidName())
//                    .flag(false)
//                    .build();
//        }
//
//        boolean isChatTimeBeforeLastVisitTime = chat.getChatTime().isBefore(r.getLastVisitTime());
//        return RoomResponseDto.builder()
//                .roomId(r.getRoomId())
//                .kidId(r.getKid().getKidId())
//                .kidName(r.getKid().getKidName())
//                .lastChat(chat.getChatContent())
//                .flag(isChatTimeBeforeLastVisitTime)
//                .build();
    return null;
}
    public void addRoom(Long teacherId, Long kidId) {

    }
}
