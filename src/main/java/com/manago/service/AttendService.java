package com.manago.service;

import com.manago.repository.AttendRepository;
import com.manago.vo.Attend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttendService {

    private final AttendRepository attendRepository;

    // 오늘 날짜 출결 조회
    public Attend getTodayAttend(int empNo) {
        return attendRepository.findByEmpNoAndWorkDate(empNo, LocalDate.now());
    }

    // 출근 처리
    public void checkIn(int empNo) {
        LocalDate today = LocalDate.now();
        Attend attend = attendRepository.findByEmpNoAndWorkDate(empNo, today);

        if (attend == null) {
            attend = new Attend();
            attend.setEmpNo(empNo);
            attend.setWorkDate(today);
        }

        attend.setCheckIn(LocalDateTime.now());
        attendRepository.save(attend);
    }


    // 퇴근 처리
    public void checkOut(int empNo) {
        LocalDate today = LocalDate.now();
        Attend attend = attendRepository.findByEmpNoAndWorkDate(empNo, today);

        if (attend != null) {
            attend.setCheckOut(LocalDateTime.now());
            attendRepository.save(attend);
        }
    }

    public boolean hasCheckedInToday(int empNo) {
        Attend attend = attendRepository.findByEmpNoAndWorkDate(empNo, LocalDate.now());
        return attend != null && attend.getCheckIn() != null;
    }

    public boolean hasCheckedOutToday(int empNo) {
        Attend attend = attendRepository.findByEmpNoAndWorkDate(empNo, LocalDate.now());
        return attend != null && attend.getCheckOut() != null;
    }

}
