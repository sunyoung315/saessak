package com.ssafy.saessak.alarm.service;

import com.ssafy.saessak.alarm.domain.Alarm;
import com.ssafy.saessak.alarm.dto.AlarmRequestDto;
import com.ssafy.saessak.alarm.dto.AlarmResponseDto;
import com.ssafy.saessak.alarm.repository.AlarmRepository;
import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.oauth.service.ParentService;
import com.ssafy.saessak.oauth.service.TeacherService;
import com.ssafy.saessak.user.domain.*;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final KidRepository kidRepository;
    private final AuthenticationService authenticationService;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;

    @Transactional
    public Long insertAlarm(AlarmRequestDto requestDto) {
        Kid findKid = kidRepository.findById(requestDto.getKidId())
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        Alarm alarm = Alarm.builder()
                .kid(findKid)
                .alarmType(requestDto.getAlarmType())
                .alarmDate(requestDto.getAlarmDate())
                .alarmContent(requestDto.getAlarmContent())
                .build();

        Alarm savedAlarm = alarmRepository.save(alarm);
        return savedAlarm.getAlarmId();
    }

    public List<AlarmResponseDto> alarmListOfParent(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        List<String> alarmTypeList = new ArrayList<>();
        alarmTypeList.add("등원 알림");
        alarmTypeList.add("하원 알림");
        alarmTypeList.add("대리인 알림");
        List<Alarm> alarmList = alarmRepository.findByKidAndAlarmTypeIn(kid, alarmTypeList);

        List<AlarmResponseDto> responseDtoList = new ArrayList<>();

        for(Alarm alarm : alarmList) {
            AlarmResponseDto alarmResponseDto = AlarmResponseDto.builder()
                    .alarmId(alarm.getAlarmId())
                    .kidName(kid.getNickname())
                    .alarmType(alarm.getAlarmType())
                    .alarmDate(alarm.getAlarmDate())
                    .alarmContent(alarm.getAlarmContent())
                    .build();

            responseDtoList.add(alarmResponseDto);
        }

        return responseDtoList;
    }

    public List<AlarmResponseDto> alarmListOfTeacher() {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        List<Kid> kidList = kidRepository.findAllByClassroom(classroom);

        List<String> alarmTypeList = new ArrayList<>();
        alarmTypeList.add("귀가동의서 알림");
        alarmTypeList.add("알러지 알림");

        List<AlarmResponseDto> responseDtoList = new ArrayList<>();

        for(Kid kid : kidList) {
            List<Alarm> alarmList = alarmRepository.findByKidAndAlarmTypeIn(kid, alarmTypeList);

            for(Alarm alarm : alarmList) {
                AlarmResponseDto alarmResponseDto = AlarmResponseDto.builder()
                        .alarmId(alarm.getAlarmId())
                        .kidName(kid.getNickname())
                        .alarmType(alarm.getAlarmType())
                        .alarmDate(alarm.getAlarmDate())
                        .alarmContent(alarm.getAlarmContent())
                        .build();

                responseDtoList.add(alarmResponseDto);
            }
        }

        return responseDtoList;
    }

    @Transactional
    public void delete(Long alarmId) {
        alarmRepository.deleteById(alarmId);
    }

    @Transactional
    public void deleteAll() {
        User user = authenticationService.getUserByAuthentication();

        try {
            List<String> alarmTypeList = new ArrayList<>();

            Optional<Parent> parent = parentRepository.findById(user.getId());
            if (parent.isPresent()) {
                alarmTypeList.add("등원 알림");
                alarmTypeList.add("하원 알림");
                alarmTypeList.add("대리인 알림");
            }
            Optional<Teacher> teacher = teacherRepository.findById(user.getId());
            if (teacher.isPresent()) {
                alarmTypeList.add("귀가동의서 알림");
                alarmTypeList.add("알러지 알림");
            }

            List<Alarm> alarmList = alarmRepository.findByAlarmTypeIn(alarmTypeList);
            for (Alarm alarm : alarmList) {
                alarmRepository.delete(alarm);
            }
        } catch(Exception e) {
            throw new UserException(ExceptionCode.PARENT_OR_TEACHER_NOT_FOUND);
        }
    }
}
