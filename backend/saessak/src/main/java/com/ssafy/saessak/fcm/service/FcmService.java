package com.ssafy.saessak.fcm.service;

import com.ssafy.saessak.alarm.domain.Alarm;
import com.ssafy.saessak.alarm.dto.AlarmRequestDto;
import com.ssafy.saessak.alarm.repository.AlarmRepository;
import com.ssafy.saessak.alarm.service.AlarmService;
import com.ssafy.saessak.attendance.dto.AttendanceTimeResponseDto;
import com.ssafy.saessak.attendance.dto.ReplacementResponseDto;
import com.ssafy.saessak.fcm.dto.FcmNotificationRequestDto;
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
import com.ssafy.saessak.user.service.UserService;
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
import java.util.Optional;


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
    private final UserService userService;
    private final AlarmService alarmService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    @Transactional
    public void saveToken(FcmTokenRequestDto requestDto) {
        User user = authenticationService.getUserByAuthentication();

        Optional<Teacher> teacher = teacherRepository.findById(user.getId());
        if(teacher.isPresent()) {
            teacher.get().setToken(requestDto.getToken());
        }

        Optional<Parent> parent = parentRepository.findById(user.getId());
        if(parent.isPresent()) {
            parent.get().setToken(requestDto.getToken());
        }
    }

    @Transactional
    public void changeAlarm() {
        User user = authenticationService.getUserByAuthentication();

        Optional<Teacher> teacher = teacherRepository.findById(user.getId());
        if(teacher.isPresent()) {
            teacher.get().setAlarm();
        }

        Optional<Parent> parent = parentRepository.findById(user.getId());
        if(parent.isPresent()) {
            parent.get().setAlarm();
        }
    }

    public void sendInTime(AttendanceTimeResponseDto responseDto, Long kidId) {
        String TITLE = "등원 알림";
        String parentToken = userService.getParentToken(kidId);

        FcmNotificationRequestDto.Notification notification = FcmNotificationRequestDto.Notification.builder()
                .token(parentToken)
                .title(TITLE)
                .body(responseDto.getKidName()+" 어린이가 "+ responseDto.getAttendanceDate()+"일 "+ responseDto.getAttendanceTime().format(formatter) + "에 등원했습니다")
                .build();
        FcmNotificationRequestDto fcmRequestDto = FcmNotificationRequestDto.builder()
                .notification(notification)
                .build();
        System.out.println(notification.getBody());

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(kidId)
                .alarmType(TITLE)
                .alarmDate(responseDto.getAttendanceDate())
                .alarmContent(responseDto.getAttendanceTime())
                .build();

        sendNotification(fcmRequestDto);
        alarmService.insertAlarm(alarmRequestDto);
    }

    public void sendOutTime(AttendanceTimeResponseDto responseDto, Long kidId) {
        String TITLE = "하원 알림";
        String parentToken = userService.getParentToken(kidId);

        FcmNotificationRequestDto.Notification notification = FcmNotificationRequestDto.Notification.builder()
                .token(parentToken)
                .title(TITLE)
                .body(responseDto.getKidName() + " 어린이가 " + responseDto.getAttendanceDate() + "일 " + responseDto.getAttendanceTime().format(formatter) + "에 하원했습니다")
                .build();
        FcmNotificationRequestDto fcmRequestDto = FcmNotificationRequestDto.builder()
                .notification(notification)
                .build();

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(kidId)
                .alarmType(TITLE)
                .alarmDate(responseDto.getAttendanceDate())
                .alarmContent(responseDto.getAttendanceTime())
                .build();

        sendNotification(fcmRequestDto);
        alarmService.insertAlarm(alarmRequestDto);
    }

    public void sendReplacement(ReplacementResponseDto replacementResponseDto, Long kidId) {
        String TITLE = "대리인 알림";
        String parentToken = userService.getParentToken(kidId);

        FcmNotificationRequestDto.Notification notification = FcmNotificationRequestDto.Notification.builder()
                .token(parentToken)
                .title(TITLE)
                .body(replacementResponseDto.getReplacementRelationship()+
                        "("+replacementResponseDto.getReplacementName()+") 님이 "+replacementResponseDto.getKidName()+" 어린이와 함께 귀가했습니다")
                .build();
        FcmNotificationRequestDto fcmRequestDto = FcmNotificationRequestDto.builder()
                .notification(notification)
                .build();

        AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                .kidId(kidId)
                .alarmType(TITLE)
                .build();

        sendNotification(fcmRequestDto);
        alarmService.insertAlarm(alarmRequestDto);
    }

    public void sendNotification(FcmNotificationRequestDto requestDto) {
        try {
            // Firebase Admin SDK 초기화
            firebaseInit.init();

            // 메시지 생성
            Message message = Message.builder()
                    .setToken(requestDto.getNotification().getToken()) // 수신자의 FCM 토큰
                    .setNotification(Notification.builder()
                            .setTitle(requestDto.getNotification().getTitle()) // 알림 제목
                            .setBody(requestDto.getNotification().getBody()) // 알림 내용
                            .build())
                    .build();

            FirebaseMessaging.getInstance().send(message); // 알림 보내기

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendScheduledLunchAllergyNotification() {
        List<Kid> kidList = kidRepository.findAll();
        for(Kid kid : kidList) {
            String findKidAllergy = kid.getKidAllergy();
            String[] kidAllergyList = findKidAllergy.split("/");

            Menu menu = menuRepository.findByMenuDateAndMenuType(LocalDate.now(), "점심");
            if(menu != null) {
                List<Food> foodList = foodRepository.findByMenu(menu);

                HashSet<Integer> set = new HashSet<Integer>();

                for (Food food : foodList) {
                    String[] foodAllergyList = food.getFoodAllergy().split("/");
                    for (String foodAllergy : foodAllergyList) {
                        set.add(Integer.parseInt(foodAllergy));
                    }
                }

                boolean alarmFlag = false;

                for (String kidAllgery : kidAllergyList) {
                    if (set.contains(Integer.parseInt(kidAllgery))) {
                        alarmFlag = true;
                        break;
                    }
                }

                if (alarmFlag) {
                    // 학부모에게 알림
                    Parent parent = kid.getParent();
                    FcmNotificationRequestDto.Notification notification1 = FcmNotificationRequestDto.Notification.builder()
                            .token(parent.getParentDevice())
                            .title("알러지 알림")
                            .body(LocalDate.now() + "일에 " + menu.getMenuType() + "식단에 " + kid.getNickname() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                            .build();
                    FcmNotificationRequestDto fcmNotificationRequestDto1 = FcmNotificationRequestDto.builder()
                            .notification(notification1)
                            .build();
                    sendNotification(fcmNotificationRequestDto1);

                    // 반에 있는 선생님들에게 알림
                    Classroom classroom = kid.getClassroom();
                    List<Teacher> teacherList = teacherRepository.findAllByClassroom(classroom);
                    for(Teacher teacher : teacherList) {
                        FcmNotificationRequestDto.Notification notification2 = FcmNotificationRequestDto.Notification.builder()
                                .token(teacher.getTeacherDevice())
                                .title("알러지 알림")
                                .body(LocalDate.now() + "일에 " + menu.getMenuType() + "식단에 " + kid.getNickname() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                                .build();
                        FcmNotificationRequestDto fcmNotificationRequestDto2 = FcmNotificationRequestDto.builder()
                                .notification(notification1)
                                .build();
                        sendNotification(fcmNotificationRequestDto2);
                    }

                    AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                            .kidId(kid.getId())
                            .alarmType("알러지 알림")
                            .alarmDate(LocalDate.now())
                            .build();
                    insertAlarm(alarmRequestDto);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 15 * * ?")
    public void sendScheduledSnackAllergyNotification() {
        List<Kid> kidList = kidRepository.findAll();
        for(Kid kid : kidList) {
            String findKidAllergy = kid.getKidAllergy();
            String[] kidAllergyList = null;
            if(findKidAllergy != null) kidAllergyList = findKidAllergy.split("/");

            Menu menu = menuRepository.findByMenuDateAndMenuType(LocalDate.now(), "점심");

            if(menu != null) {
                List<Food> foodList = foodRepository.findByMenu(menu);

                HashSet<Integer> set = new HashSet<Integer>();

                for (Food food : foodList) {
                    String[] foodAllergyList = food.getFoodAllergy().split("/");
                    for (String foodAllergy : foodAllergyList) {
                        set.add(Integer.parseInt(foodAllergy));
                    }
                }

                boolean alarmFlag = false;

                for (String kidAllgery : kidAllergyList) {
                    if (set.contains(Integer.parseInt(kidAllgery))) {
                        alarmFlag = true;
                        break;
                    }
                }

                if (alarmFlag) {
                    // 학부모에게 알림
                    Parent parent = kid.getParent();
                    FcmNotificationRequestDto.Notification notification1 = FcmNotificationRequestDto.Notification.builder()
                            .token(parent.getParentDevice())
                            .title("알러지 알림")
                            .body(LocalDate.now() + "일에 " + menu.getMenuType() + "식단에 " + kid.getNickname() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                            .build();
                    FcmNotificationRequestDto fcmNotificationRequestDto1 = FcmNotificationRequestDto.builder()
                            .notification(notification1)
                            .build();
                    sendNotification(fcmNotificationRequestDto1);

                    // 반에 있는 선생님들에게 알림
                    Classroom classroom = kid.getClassroom();
                    List<Teacher> teacherList = teacherRepository.findAllByClassroom(classroom);
                    for(Teacher teacher : teacherList) {
                        FcmNotificationRequestDto.Notification notification2 = FcmNotificationRequestDto.Notification.builder()
                                .token(teacher.getTeacherDevice())
                                .title("알러지 알림")
                                .body(LocalDate.now() + "일에 " + menu.getMenuType() + "식단에 " + kid.getNickname() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                                .build();
                        FcmNotificationRequestDto fcmNotificationRequestDto2 = FcmNotificationRequestDto.builder()
                                .notification(notification1)
                                .build();
                        sendNotification(fcmNotificationRequestDto2);
                    }

                    AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                            .kidId(kid.getId())
                            .alarmType("알러지 알림")
                            .alarmDate(LocalDate.now())
                            .build();
                    insertAlarm(alarmRequestDto);
                }
            }
        }
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
