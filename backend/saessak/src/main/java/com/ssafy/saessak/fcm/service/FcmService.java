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
import com.ssafy.saessak.fcm.domain.FcmToken;
import com.ssafy.saessak.fcm.dto.FcmResponseDto;
import com.ssafy.saessak.fcm.dto.FcmTokenRequestDto;
import com.ssafy.saessak.fcm.repository.FcmRepository;
import com.ssafy.saessak.fcm.util.FirebaseInit;
import com.ssafy.saessak.menu.domain.Food;
import com.ssafy.saessak.menu.domain.Menu;
import com.ssafy.saessak.menu.repository.FoodRepository;
import com.ssafy.saessak.menu.repository.MenuRepository;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.user.domain.*;
import com.ssafy.saessak.user.repository.*;
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

    private final KidRepository kidRepository;
    private final MenuRepository menuRepository;
    private final FoodRepository foodRepository;
    private final AlarmRepository alarmRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticationService authenticationService;
    private final AlarmService alarmService;
    private final FcmRepository fcmRepository;
    private final ClassroomRepository classroomRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    @Transactional
    public void saveToken(FcmTokenRequestDto requestDto) {
        User user = authenticationService.getUserByAuthentication();
        // 갱신을 위해 기존에 토큰이 있으면 삭제
        Optional<FcmToken> fcmToken = fcmRepository.findById(user.getId());
        if(fcmToken.isPresent()) {
            fcmRepository.delete(fcmToken.get());
        }
        fcmRepository.save(FcmToken.of(user.getId(), requestDto.getToken()));
    }

    @Transactional
    public void deleteToken() {
        User user = authenticationService.getUserByAuthentication();

        FcmToken fcmToken = fcmRepository.findById(user.getId())
                .orElseThrow(() -> new FcmException(ExceptionCode.FCM_TOKEN_NOT_FOUND));
        fcmRepository.delete(fcmToken);
    }

    public String getParentToken(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        Parent parent = kid.getParent();
        if(parent == null) return null;

        Optional<FcmToken> fcmToken = fcmRepository.findById(parent.getId());
        if(fcmToken.isPresent()) return fcmToken.get().getFcmToken();
        return null;
    }

    public String getUserToken(Long userId) {
        Optional<FcmToken> fcmToken = fcmRepository.findById(userId);
        if(fcmToken.isPresent()) return fcmToken.get().getFcmToken();
        return null;
    }

    public void sendInTime(AttendanceTimeResponseDto responseDto, Long kidId) {
        String TITLE = "등원 알림";
        String parentToken = getParentToken(kidId);
        String messageBody = responseDto.getKidName()+" 어린이가 "+ responseDto.getAttendanceDate()+"일 "+ responseDto.getAttendanceTime().format(formatter) + "에 등원했습니다";

        sendNotification(FcmResponseDto.of(parentToken, TITLE, messageBody));
        alarmService.insertAlarm(AlarmRequestDto.of(kidId, TITLE, responseDto.getAttendanceDate(), responseDto.getAttendanceTime()));
    }

    public void sendOutTime(AttendanceTimeResponseDto responseDto, Long kidId) {
        String TITLE = "하원 알림";
        String parentToken = getParentToken(kidId);
        String messageBody = responseDto.getKidName() + " 어린이가 " + responseDto.getAttendanceDate() + "일 " + responseDto.getAttendanceTime().format(formatter) + "에 하원했습니다";

        sendNotification(FcmResponseDto.of(parentToken, TITLE, messageBody));
        alarmService.insertAlarm(AlarmRequestDto.of(kidId, TITLE, responseDto.getAttendanceDate(), responseDto.getAttendanceTime()));
    }

    public void sendReplacement(ReplacementParentAlarmResponseDto replacementResponseDto, Long kidId) {
        String TITLE = "대리인 알림";
        String parentToken = getParentToken(kidId);
        String messageBody = replacementResponseDto.getReplacementRelationship()+
                "("+replacementResponseDto.getReplacementName()+") 님이 "+replacementResponseDto.getKidName()+" 어린이와 함께 귀가했습니다";

        sendNotification(FcmResponseDto.of(parentToken, TITLE, messageBody));
        alarmService.insertAlarm(AlarmRequestDto.of(kidId, TITLE, null, null));
    }

    public void sendInsertReplacement(ReplacementTeacherAlarmResponseDto responseDto) {
        String TITLE = "귀가동의서 알림";
        String messageBody = responseDto.getKidName() + " 어린이의 대리인 귀가 동의서가 등록되었습니다";

        Classroom classroom = classroomRepository.findById(responseDto.getClassroomId())
                .orElseThrow(() -> new UserException(ExceptionCode.CLASSROOM_NOT_FOUND));
        List<Teacher> teacherList = teacherRepository.findAllByClassroom(classroom);
        for(Teacher teacher : teacherList) {
            String teacherToken = getUserToken(teacher.getId());

            sendNotification(FcmResponseDto.of(teacherToken, TITLE, messageBody));
        }

        alarmService.insertAlarm(AlarmRequestDto.of(responseDto.getKidId(), TITLE, LocalDate.now(), null));
    }

    public void sendNotification(FcmResponseDto fcmResponseDto) {
        if(fcmResponseDto.getToken() != null) {
            try {
                firebaseInit.init(); // Firebase Admin SDK 초기화
                
                Message message = Message.builder() // 메시지 생ㅅ어
                        .setToken(fcmResponseDto.getToken())
                        .setNotification(Notification.builder()
                                .setTitle(fcmResponseDto.getTitle())
                                .setBody(fcmResponseDto.getBody())
                                .build())
                        .putData("click_action", "https://i10a706.p.ssafy.io")
                        .build();

                FirebaseMessaging.getInstance().sendAsync(message); // 알림 보내기

            } catch (Exception e) {
                throw new FcmException(ExceptionCode.FAIL_FCM_ALARM);
            }
        }
    }

    public boolean checkKidAndAllergy(Kid kid, String menuType) {
        String findKidAllergy = kid.getKidAllergy();
        if(findKidAllergy == null) return false;
        String[] kidAllergyList = findKidAllergy.split("/");

        Menu menu = menuRepository.findByMenuDateAndMenuType(LocalDate.now(), menuType)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.MENU_DATE_AND_TYPE_NOT_FOUND));

        List<Food> foodList = foodRepository.findByMenu(menu);

        HashSet<Integer> set = new HashSet<Integer>();

        for (Food food : foodList) {
            if(food.getFoodAllergy().length() > 0) {
                String[] foodAllergyList = food.getFoodAllergy().split("/");
                for (String foodAllergy : foodAllergyList) {
                    set.add(Integer.parseInt(foodAllergy));
                }
            }
        }

        for (String kidAllergy : kidAllergyList) {
            if (set.contains(Integer.parseInt(kidAllergy))) {
                return true;
            }
        }

        return false;
    }

    public void sendAllergyParent(Kid kid, String menuType) {
        String TITLE = "알러지 알림";
        String parentToken = getParentToken(kid.getId());
        String messageBody = LocalDate.now() + "일에 " + menuType + "식단에 " + kid.getNickname() + " 어린이의 알러지를 유발하는 음식이 존재합니다";

        sendNotification(FcmResponseDto.of(parentToken, TITLE, messageBody));
    }

    public void sendAllergyTeacher(Kid kid, String menuType) {
        String TITLE = "알러지 알림";
        String messageBody = LocalDate.now() + "일에 " + menuType + "식단에 " + kid.getNickname() + " 어린이의 알러지를 유발하는 음식이 존재합니다";

        Classroom classroom = kid.getClassroom();
        List<Teacher> teacherList = teacherRepository.findAllByClassroom(classroom);
        for(Teacher teacher : teacherList) {
            String teacherToken = getUserToken(teacher.getId());

            sendNotification(FcmResponseDto.of(teacherToken, TITLE, messageBody));
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendScheduledLunchAllergyNotification() {
        String menuType = "점심";
        String TITLE = "알러지 알림";
        List<Kid> kidList = kidRepository.findAll();
        for(Kid kid : kidList) {
            if(checkKidAndAllergy(kid, menuType)) {
                sendAllergyParent(kid, menuType);
                sendAllergyTeacher(kid, menuType);

                insertAlarm(AlarmRequestDto.of(kid.getId(), TITLE, LocalDate.now(), null));
            }
        }
    }

    @Scheduled(cron = "0 0 15 * * ?")
    public void sendScheduledSnackAllergyNotification() {
        String menuType = "간식";
        String TITLE = "알러지 알림";
        List<Kid> kidList = kidRepository.findAll();
        for(Kid kid : kidList) {
            if(checkKidAndAllergy(kid, menuType)) {
                sendAllergyParent(kid, menuType);
                sendAllergyTeacher(kid, menuType);

                insertAlarm(AlarmRequestDto.of(kid.getId(), TITLE, LocalDate.now(), null));
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
