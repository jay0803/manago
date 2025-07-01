package com.example.manago.repository;


import com.example.manago.vo.EmpVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<EmpVO, Integer> {
    EmpVO findByEmpIdAndEmpPw(String empId, String empPw);
    EmpVO findByEmpNmAndEmpTel(String empNm, String empTel);
}
