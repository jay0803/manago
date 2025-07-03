package com.manago.repository;

import com.manago.vo.Attend;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface AttendRepository extends JpaRepository<Attend, Integer> {
    Attend findByEmpNoAndWorkDate(int empNo, LocalDate workDate);
}
