package com.ssafy.saessak.attendance.service;

import com.ssafy.saessak.attendance.domain.Attendance;
import com.ssafy.saessak.attendance.dto.*;
import com.ssafy.saessak.attendance.repository.AttendanceRepository;
import com.ssafy.saessak.document.domain.Replacement;
import com.ssafy.saessak.document.repository.ReplacementRepository;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ClassroomRepository classroomRepository;
    private final KidRepository kidRepository;
    private final ReplacementRepository replacementRepository;

    @Transactional
    public AttendanceTimeResponseDto inTime(Long kidId) {
        Kid kid = kidRepository.findById(kidId).get();
        Attendance attendance = Attendance.builder()
                .attendanceDate(LocalDate.now())
                .attendanceInTime(LocalTime.now())
                .kid(kid)
                .build();

        Attendance savedAttendance = attendanceRepository.save(attendance);
        kid.getAttendanceList().add(attendance);

        return AttendanceTimeResponseDto.builder()
                .kidName(kid.getKidName())
                .attendanceId(savedAttendance.getAttendanceId())
                .attendanceDate(savedAttendance.getAttendanceDate())
                .attendanceTime(savedAttendance.getAttendanceInTime())
                .build();
    }

    @Transactional
    public AttendanceTimeResponseDto outTime(Long kidId) {
        Kid kid = kidRepository.findById(kidId).get();
        Attendance attendance = attendanceRepository.findByKidAndAttendanceDate(kid, LocalDate.now());

        Attendance savedAttendance = attendance.outTime();
        return AttendanceTimeResponseDto.builder()
                .kidName(kid.getKidName())
                .attendanceId(savedAttendance.getAttendanceId())
                .attendanceDate(savedAttendance.getAttendanceDate())
                .attendanceTime(savedAttendance.getAttendanceOutTime())
                .build();
    }

    public List<AttendanceKidListResponseDto> list(AttendanceRequestDto requestDto) {
        // 반에서 아이 찾기
        Classroom classroom = classroomRepository.findById(requestDto.getClassroomId()).get();
        List<Kid> kidList = kidRepository.findAllByClassroom(classroom);
        LocalDate startDate = null;
        LocalDate endDate = null;

        if(requestDto.getWeek() != 0) {
            startDate = getStartDateOfWeek(requestDto.getYear(), requestDto.getMonth(), requestDto.getWeek());
            endDate = startDate.plusDays(6); // 일주일 더하기
        }

        List<AttendanceKidListResponseDto> attendanceKidListResponseDtoList = new ArrayList<>();

        for(Kid k : kidList) {
            List<Attendance> attendanceList = attendanceRepository.findAllByAttendanceDateBetweenAndKid(startDate, endDate, k);
            List<AttendanceKidResponseDto> attendanceKidResponseDtoList = new ArrayList<>();
            for(Attendance attendance : attendanceList) {
                AttendanceKidResponseDto attendanceKidResponseDto = AttendanceKidResponseDto.builder()
                        .attendanceId(attendance.getAttendanceId())
                        .attendanceDate(attendance.getAttendanceDate())
                        .attendanceInTime(attendance.getAttendanceInTime())
                        .attendanceOutTime(attendance.getAttendanceOutTime())
                        .build();
                attendanceKidResponseDtoList.add(attendanceKidResponseDto);
            }
            AttendanceKidListResponseDto attendanceKidListResponseDto = AttendanceKidListResponseDto.builder()
                    .kidId(k.getId())
                    .kidName(k.getKidName())
                    .attendance(attendanceKidResponseDtoList)
                    .build();

            attendanceKidListResponseDtoList.add(attendanceKidListResponseDto);
        }

        return attendanceKidListResponseDtoList;
    }

    // 해당 주차의 시작일을 계산
    private LocalDate getStartDateOfWeek(int year, int month, int week) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstDayOfMonth.get(weekFields.dayOfWeek());
        int daysToAdd = (week-1)*7 - firstDayOfWeek + 1;

        return firstDayOfMonth.plusDays(daysToAdd);
    }
    
    public ReplacementResponseDto checkReplacement(Long kidId) {
        Kid kid = kidRepository.findById(kidId).get();
        List<Replacement> replacementList = replacementRepository.findByKid(kid);

        boolean check = false;
        for(Replacement replacement : replacementList) {
            if(replacement.getReplacementDate()==LocalDate.now()) {
                LocalTime replacementTime = LocalTime.parse(replacement.getReplacementTime());
                long timegap = ChronoUnit.MINUTES.between(replacementTime, LocalTime.now());
                if(Math.abs(timegap) <= 15) {
                    ReplacementResponseDto replacementResponseDto = ReplacementResponseDto.builder()
                            .kidName(kid.getKidName())
                            .replacementName(replacement.getReplacementName())
                            .replacementRelationship(replacement.getReplacementRelationship())
                            .build();
                    return replacementResponseDto;
                }
            }
        }

        return null;
    }
}
