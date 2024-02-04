package com.ssafy.saessak.fcm.service;

import com.ssafy.saessak.alarm.domain.Alarm;
import com.ssafy.saessak.alarm.dto.AlarmRequestDto;
import com.ssafy.saessak.alarm.repository.AlarmRepository;
import com.ssafy.saessak.alarm.service.AlarmService;
import com.ssafy.saessak.attendance.dto.AttendanceTimeResponseDto;
import com.ssafy.saessak.attendance.dto.ReplacementParentAlarmResponseDto;
import com.ssafy.saessak.document.dto.ReplacementTeacherAlarmResponseDto;
import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.FcmException;
import com.ssafy.saessak.exception.model.NotFoundException;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.fcm.dto.FcmResponseDto;
import com.ssafy.saessak.fcm.dto.FcmTokenRequestDto;
import com.ssafy.saessak.fcm.util.FirebaseInit;
import com.ssafy.saessak.menu.domain.Food;
import com.ssafy.saessak.menu.domain.Menu;
import com.ssafy.saessak.menu.repository.FoodRepository;
import com.ssafy.saessak.menu.repository.MenuRepository;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.user.domain.*;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseInit firebaseInit;

    private final ParentRepository parentRepository;
    private final KidRepository kidRepository;
    private final MenuRepository menuRepository;
    private final FoodRepository foodRepository;
    private final AlarmRepository alarmRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticationService authenticationService;
    private final AlarmService alarmService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    @Transactional
    public void saveToken(FcmTokenRequestDto requestDto) {
        User user = authenticationService.getUserByAuthentication();

        Teacher teacher = teacherRepository.findById(user.getId())
                .orElseThrow(() -> new UserException(ExceptionCode.TEACHER_NOT_FOUND));
        teacher.setToken(requestDto.getToken());

        Parent parent = parentRepository.findById(user.getId())
                .orElseThrow(() -> new UserException(ExceptionCode.PARENT_NOT_FOUND));
        parent.setToken(requestDto.getToken());
    }

    @Transactional
    public void changeAlarm() {
        User user = authenticationService.getUserByAuthentication();

        Teacher teacher = teacherRepository.findById(user.getId())
                .orElseThrow(() -> new UserException(ExceptionCode.TEACHER_NOT_FOUND));
        teacher.setAlarm();

        Parent parent = parentRepository.findById(user.getId())
                .orElseThrow(() -> new UserException(ExceptionCode.PARENT_NOT_FOUND));
        parent.setAlarm();
    }

    public void sendInTime(AttendanceTimeResponseDto responseDto, Long kidId) {
        String TITLE = "등원 알림";
        String parentToken = getParentToken(kidId);

        FcmResponseDto fcmResponseDto = FcmResponseDto.builder()
                .token(parentToken)
                .title(TITLE)
                .body(responseDto.getKidName()+" 어린이가 "+ responseDto.getAttendanceDate()+"일 "+ responseDto.getAttendanceTime().format(formatter) + "에 등원했습니다")
                .build();

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(kidId)
                .alarmType(TITLE)
                .alarmDate(responseDto.getAttendanceDate())
                .alarmContent(responseDto.getAttendanceTime())
                .build();

        sendNotification(fcmResponseDto);
        alarmService.insertAlarm(alarmRequestDto);
    }

    public void sendOutTime(AttendanceTimeResponseDto responseDto, Long kidId) {
        String TITLE = "하원 알림";
        String parentToken = getParentToken(kidId);

        FcmResponseDto fcmResponseDto = FcmResponseDto.builder()
                .token(parentToken)
                .title(TITLE)
                .body(responseDto.getKidName() + " 어린이가 " + responseDto.getAttendanceDate() + "일 " + responseDto.getAttendanceTime().format(formatter) + "에 하원했습니다")
                .build();

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(kidId)
                .alarmType(TITLE)
                .alarmDate(responseDto.getAttendanceDate())
                .alarmContent(responseDto.getAttendanceTime())
                .build();

        sendNotification(fcmResponseDto);
        alarmService.insertAlarm(alarmRequestDto);
    }

    public void sendReplacement(ReplacementParentAlarmResponseDto replacementResponseDto, Long kidId) {
        String TITLE = "대리인 알림";
        String parentToken = getParentToken(kidId);

        FcmResponseDto fcmResponseDto = FcmResponseDto.builder()
                .token(parentToken)
                .title(TITLE)
                .body(replacementResponseDto.getReplacementRelationship()+
                        "("+replacementResponseDto.getReplacementName()+") 님이 "+replacementResponseDto.getKidName()+" 어린이와 함께 귀가했습니다")
                .build();

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(kidId)
                .alarmType(TITLE)
                .build();

        sendNotification(fcmResponseDto);
        alarmService.insertAlarm(alarmRequestDto);
    }

    public void sendInsertReplacement(ReplacementTeacherAlarmResponseDto responseDto) {
        String TITLE = "귀가동의서 알림";
        FcmResponseDto fcmResponseDto = FcmResponseDto.builder()
                .token(responseDto.getTeacherDevice())
                .title(TITLE)
                .body(responseDto.getKidName() + " 어린이의 대리인 귀가 동의서가 등록되었습니다")
                .build();

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(responseDto.getKidId())
                .alarmType(TITLE)
                .alarmDate(LocalDate.now())
                .build();

        sendNotification(fcmResponseDto);
        alarmService.insertAlarm(alarmRequestDto);
    }

    public void sendNotification(FcmResponseDto fcmResponseDto) {
        try {
            // Firebase Admin SDK 초기화
            firebaseInit.init();
            
            // 메시지 생성
            Message message = Message.builder()
                    .setToken(fcmResponseDto.getToken()) // 수신자의 FCM 토큰
                    .setNotification(Notification.builder()
                            .setTitle(fcmResponseDto.getTitle()) // 알림 제목
                            .setBody(fcmResponseDto.getBody()) // 알림 내용
                            .build())
                    .build();
            
            // 토큰이 없는경우 || 토큰 만료된 경우

            FirebaseMessaging.getInstance().send(message); // 알림 보내기

        } catch (Exception e) {
            throw new FcmException(ExceptionCode.FAIL_FCM_ALARM);
        }
    }

    public boolean checkKidAndAllergy(Kid kid, String menuType) {
        String findKidAllergy = kid.getKidAllergy();
        String[] kidAllergyList = findKidAllergy.split("/");

        Menu menu = menuRepository.findByMenuDateAndMenuType(LocalDate.now(), menuType)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.MENU_DATE_AND_TYPE_NOT_FOUND));

        List<Food> foodList = foodRepository.findByMenu(menu);

        HashSet<Integer> set = new HashSet<Integer>();

        for (Food food : foodList) {
            String[] foodAllergyList = food.getFoodAllergy().split("/");
            for (String foodAllergy : foodAllergyList) {
                set.add(Integer.parseInt(foodAllergy));
            }
        }

        for (String kidAllgery : kidAllergyList) {
            if (set.contains(Integer.parseInt(kidAllgery))) {
                return true;
            }
        }

        return false;
    }

    public void sendAllergyParent(Kid kid, String menuType) {
        String TITLE = "알러지 알림";
        Parent parent = kid.getParent();

        FcmResponseDto fcmResponseDto = FcmResponseDto.builder()
                .token(parent.getParentDevice())
                .title(TITLE)
                .body(LocalDate.now() + "일에 " + menuType + "식단에 " + kid.getNickname() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                .build();

        sendNotification(fcmResponseDto);
    }

    public void sendAllergyTeacher(Kid kid, String menuType) {
        String TITLE = "알러지 알림";
        Classroom classroom = kid.getClassroom();
        List<Teacher> teacherList = teacherRepository.findAllByClassroom(classroom);
        for(Teacher teacher : teacherList) {
            FcmResponseDto fcmResponseDto = FcmResponseDto.builder()
                    .token(teacher.getTeacherDevice())
                    .title(TITLE)
                    .body(LocalDate.now() + "일에 " + menuType + "식단에 " + kid.getNickname() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                    .build();
            sendNotification(fcmResponseDto);
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendScheduledLunchAllergyNotification() {
        String menuType = "점심";
        List<Kid> kidList = kidRepository.findAll();
        for(Kid kid : kidList) {
            if(checkKidAndAllergy(kid, menuType)) {
                sendAllergyParent(kid, menuType);
                sendAllergyTeacher(kid, menuType);

                AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                        .kidId(kid.getId())
                        .alarmType("알러지 알림")
                        .alarmDate(LocalDate.now())
                        .build();
                insertAlarm(alarmRequestDto);
            }
        }
    }

    @Scheduled(cron = "0 0 15 * * ?")
    public void sendScheduledSnackAllergyNotification() {
        String menuType = "간식";
        List<Kid> kidList = kidRepository.findAll();
        for(Kid kid : kidList) {
            if(checkKidAndAllergy(kid, menuType)) {
                sendAllergyParent(kid, menuType);
                sendAllergyTeacher(kid, menuType);

                AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                        .kidId(kid.getId())
                        .alarmType("알러지 알림")
                        .alarmDate(LocalDate.now())
                        .build();
                insertAlarm(alarmRequestDto);
            }
        }
    }

    public String getParentToken(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        Parent parent = kid.getParent();
        return parent.getParentDevice();
    }

    @Transactional
    public Long insertAlarm(AlarmRequestDto requestDto) {
        Kid findKid = kidRepository.findById(requestDto.getKidId()).get();
        Alarm alarm = Alarm.builder()
                .kid(findKid)
                .alarmType(requestDto.getAlarmType())
                .alarmDate(requestDto.getAlarmDate())
                .alarmContent(requestDto.getAlarmContent())
                .build();

        Alarm savedAlarm = alarmRepository.save(alarm);
        return savedAlarm.getAlarmId();
    }

}
