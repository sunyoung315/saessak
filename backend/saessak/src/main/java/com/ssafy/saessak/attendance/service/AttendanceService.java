package com.ssafy.saessak.attendance.service;

import com.ssafy.saessak.attendance.domain.Attendance;
import com.ssafy.saessak.attendance.dto.*;
import com.ssafy.saessak.attendance.repository.AttendanceRepository;
import com.ssafy.saessak.document.domain.Replacement;
import com.ssafy.saessak.document.repository.ReplacementRepository;
import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.NotFoundException;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Daycare;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.DaycareRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final KidRepository kidRepository;
    private final DaycareRepository daycareRepository;
    private final ClassroomRepository classroomRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public AttendanceTimeResponseDto inTime(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        Attendance attendance = Attendance.builder()
                .attendanceDate(LocalDate.now())
                .attendanceInTime(LocalTime.now())
                .kid(kid)
                .build();

        Attendance savedAttendance = attendanceRepository.save(attendance);
        kid.getAttendanceList().add(attendance);

        return AttendanceTimeResponseDto.builder()
                .kidName(kid.getNickname())
                .attendanceId(savedAttendance.getAttendanceId())
                .attendanceDate(savedAttendance.getAttendanceDate())
                .attendanceTime(savedAttendance.getAttendanceInTime())
                .build();
    }

    @Transactional
    public AttendanceTimeResponseDto outTime(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        Attendance attendance = attendanceRepository.findByKidAndAttendanceDate(kid, LocalDate.now())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.ATTENDANCE_KID_AND_DATE_NOT_FOUND));

        Attendance savedAttendance = attendance.outTime();
        return AttendanceTimeResponseDto.builder()
                .kidName(kid.getNickname())
                .attendanceId(savedAttendance.getAttendanceId())
                .attendanceDate(savedAttendance.getAttendanceDate())
                .attendanceTime(savedAttendance.getAttendanceOutTime())
                .build();
    }

    public List<AttendanceKidListResponseDto> listOfteacher(AttendanceRequestDto requestDto) {
        // 유저(accessToken)의 classroom 찾기
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        // 반에서 아이 찾기
        List<Kid> kidList = kidRepository.findAllByClassroomOrderByNickname(classroom);
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
                    .kidName(k.getNickname())
                    .attendance(attendanceKidResponseDtoList)
                    .build();

            attendanceKidListResponseDtoList.add(attendanceKidListResponseDto);
        }

        return attendanceKidListResponseDtoList;
    }

    public List<AttendanceKidResponseDto> listOfParent(Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        List<Attendance> attendanceList = attendanceRepository.findAllByKid(kid);
        List<AttendanceKidResponseDto> responseDtoList = new ArrayList<>();
        for(Attendance attendance : attendanceList) {
            AttendanceKidResponseDto attendanceKidResponseDto = AttendanceKidResponseDto.builder()
                    .attendanceId(attendance.getAttendanceId())
                    .attendanceDate(attendance.getAttendanceDate())
                    .attendanceInTime(attendance.getAttendanceInTime())
                    .attendanceOutTime(attendance.getAttendanceOutTime())
                    .build();

            responseDtoList.add(attendanceKidResponseDto);
        }

        return responseDtoList;
    }

    // 해당 주차의 시작일을 계산
    private LocalDate getStartDateOfWeek(int year, int month, int week) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstDayOfMonth.get(weekFields.dayOfWeek());
        int daysToAdd = (week-1)*7 - firstDayOfWeek + 1;

        return firstDayOfMonth.plusDays(daysToAdd);
    }

    @Scheduled(cron = "0 0 23 ? * MON-FRI")
    public void checkNoClick() {
        List<Daycare> daycareList = daycareRepository.findAll();
        for(Daycare daycare : daycareList) {
            List<Classroom> classroomList = classroomRepository.findAllByDaycare(daycare);
            for(Classroom classroom : classroomList) {
                List<Kid> kidList = kidRepository.findAllByClassroom(classroom);
                for(Kid kid : kidList) {
                    if(!attendanceRepository.findByKidAndAttendanceDate(kid, LocalDate.now()).isPresent()) {
                        Attendance attendance = Attendance.builder()
                                .attendanceDate(LocalDate.now())
                                .kid(kid)
                                .build();
                        attendanceRepository.save(attendance);
                    }
                }
            }
        }
    }

}
