package com.ssafy.saessak.alarm.service;

import com.ssafy.saessak.alarm.domain.Alarm;
import com.ssafy.saessak.alarm.dto.AlarmRequestDto;
import com.ssafy.saessak.alarm.dto.AlarmResponseDto;
import com.ssafy.saessak.alarm.repository.AlarmRepository;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final KidRepository kidRepository;
    private final ClassroomRepository classroomRepository;

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

    public List<AlarmResponseDto> kidAlarmList(Long kidId) {
        Kid kid = kidRepository.findById(kidId).get();
        List<Alarm> alarmList = alarmRepository.findByKid(kid);

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

    public List<AlarmResponseDto> classroomAlarmList(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId).get();
        List<Kid> kidList = kidRepository.findAllByClassroom(classroom);

        List<AlarmResponseDto> responseDtoList = new ArrayList<>();

        for(Kid kid : kidList) {
            List<Alarm> alarmList = alarmRepository.findByKid(kid);

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
        List<Alarm> alarmList = alarmRepository.findAll();
        for(Alarm alarm : alarmList) {
            alarmRepository.delete(alarm);
        }
    }
}
