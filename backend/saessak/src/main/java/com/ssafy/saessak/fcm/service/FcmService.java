package com.ssafy.saessak.fcm.service;

import ch.qos.logback.classic.model.processor.LogbackClassicDefaultNestedComponentRules;
import com.ssafy.saessak.alarm.domain.Alarm;
import com.ssafy.saessak.alarm.dto.AlarmRequestDto;
import com.ssafy.saessak.alarm.repository.AlarmRepository;
import com.ssafy.saessak.fcm.dto.FcmNotificationRequestDto;
import com.ssafy.saessak.fcm.dto.FcmTokenRequestDto;
import com.ssafy.saessak.fcm.util.FirebaseInit;
import com.ssafy.saessak.menu.domain.Food;
import com.ssafy.saessak.menu.domain.Menu;
import com.ssafy.saessak.menu.repository.FoodRepository;
import com.ssafy.saessak.menu.repository.MenuRepository;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.domain.Teacher;
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
import java.util.HashSet;
import java.util.List;

import static java.awt.SystemColor.menu;

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


    @Transactional
    public void saveParentToken(FcmTokenRequestDto requestDto) {
        Parent parent = parentRepository.findById(requestDto.getId()).get();
        parent.setToken(requestDto.getToken());
    }

    @Transactional
    public void saveTeacherToken(FcmTokenRequestDto requestDto) {
        Teacher teacher = teacherRepository.findById(requestDto.getId()).get();
        teacher.setToken(requestDto.getToken());
    }

    @Transactional
    public void changeParentAlarm(Long parentId) {
        Parent parent = parentRepository.findById(parentId).get();
        parent.setAlarm();
    }

    @Transactional
    public void changeTeacherAlarm(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        teacher.setAlarm();
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

            // 알림 보내기
            FirebaseMessaging.getInstance().send(message);

        } catch (Exception e) {
            // 실패 시 오류 로그 찍고
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
                    Parent parent = kid.getParent();
                    FcmNotificationRequestDto.Notification notification1 = FcmNotificationRequestDto.Notification.builder()
                            .token(parent.getParentDevice())
                            .title("알러지 알림")
                            .body(LocalDate.now() + "일에 " + menu.getMenuType() + "식단에 " + kid.getKidName() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                            .build();
                    FcmNotificationRequestDto fcmNotificationRequestDto1 = FcmNotificationRequestDto.builder()
                            .notification(notification1)
                            .build();

                    FcmNotificationRequestDto.Notification notification2 = FcmNotificationRequestDto.Notification.builder()
                            .token(kid.getTeacher().getTeacherDevice())
                            .title("알러지 알림")
                            .body(LocalDate.now() + "일에 " + menu.getMenuType() + "식단에 " + kid.getKidName() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                            .build();
                    FcmNotificationRequestDto fcmNotificationRequestDto2 = FcmNotificationRequestDto.builder()
                            .notification(notification1)
                            .build();

                    AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                            .kidId(kid.getKidId())
                            .alarmType("알러지 알림")
                            .alarmDate(LocalDate.now())
                            .build();

                    sendNotification(fcmNotificationRequestDto1);
                    sendNotification(fcmNotificationRequestDto2);
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
                    Parent parent = kid.getParent();
                    FcmNotificationRequestDto.Notification notification1 = FcmNotificationRequestDto.Notification.builder()
                            .token(parent.getParentDevice())
                            .title("알러지 알림")
                            .body(LocalDate.now() + "일에 " + menu.getMenuType() + "식단에 " + kid.getKidName() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                            .build();
                    FcmNotificationRequestDto fcmNotificationRequestDto1 = FcmNotificationRequestDto.builder()
                            .notification(notification1)
                            .build();

                    FcmNotificationRequestDto.Notification notification2 = FcmNotificationRequestDto.Notification.builder()
                            .token(kid.getTeacher().getTeacherDevice())
                            .title("알러지 알림")
                            .body(LocalDate.now() + "일에 " + menu.getMenuType() + "식단에 " + kid.getKidName() + " 어린이의 알러지를 유발하는 음식이 존재합니다")
                            .build();
                    FcmNotificationRequestDto fcmNotificationRequestDto2 = FcmNotificationRequestDto.builder()
                            .notification(notification1)
                            .build();

                    AlarmRequestDto alarmRequestDto = AlarmRequestDto.builder()
                            .kidId(kid.getKidId())
                            .alarmType("알러지 알림")
                            .alarmDate(LocalDate.now())
                            .build();

                    sendNotification(fcmNotificationRequestDto1);
                    sendNotification(fcmNotificationRequestDto2);
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
