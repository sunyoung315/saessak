package com.ssafy.saessak.fcm.service;

import com.ssafy.saessak.fcm.dto.FcmNotificationRequestDto;
import com.ssafy.saessak.fcm.dto.FcmTokenRequestDto;
import com.ssafy.saessak.fcm.util.FirebaseInit;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.repository.ParentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
@RequiredArgsConstructor
public class FcmService {
    @Autowired
    FirebaseInit init;

    private final ParentRepository parentRepository;

    @Transactional
    public void saveParentToken(FcmTokenRequestDto requestDto) {
        Parent parent = parentRepository.findById(requestDto.getId()).get();
        parent.setToken(requestDto.getToken());
    }

    @Transactional
    public void changeParentAlarm(Long parentId) {
        Parent parent = parentRepository.findById(parentId).get();
        parent.setAlarm();
    }


    public void sendNotification(FcmNotificationRequestDto requestDto) {
        try {
            // Firebase Admin SDK 초기화
            init.init();

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

    @Scheduled(cron = "0/20 * * * * ?")
    public void sendScheduledNotification() {
        try {
            String serverKey = "BIcXYKZDRmHm0BU2Jkl9jCfp36MafrAgSknx2xBc61U6uBU8M4T7TL5wqDs-gV2RlPggjVEnE_jDZyaQdW9fk8Q";
            String deviceToken = "dsedTiTxZ83oiYSU06ywFF:APA91bGDsbaQTCF9Qrsgb3kNL0JwciBtXnZyCJNOzXzcphEz8k93wR0LpVxIKYmUFusECgLwaTPonvbMYdQjbzp4LlAC3Un5JjSz_8pbIftX2XjZl4z_azjOTk6M_lUZbRH_jOexrBzD";

            Message message = Message.builder()
                    .setToken(serverKey)
                    .setNotification(Notification.builder()
                            .setTitle("Scheduled Notification")
                            .setBody("This is a scheduled notification.")
                            .build())
                    .setToken(deviceToken)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notification sent successfully: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error sending notification: " + e.getMessage());
        }
    }
}
