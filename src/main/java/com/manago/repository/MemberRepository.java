package com.manago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.manago.vo.MemberVO;

@Repository
public interface MemberRepository extends JpaRepository<MemberVO, Integer> {

    Optional<MemberVO> findByEmpId(String empId);
    // 아이디 찾기
    Optional<MemberVO> findByEmpNmAndDateAndEmpTel(String empNm, String date, String empTel);
    Optional<MemberVO> findByEmpNmAndEmpTel(String empNm, String empTel);
    // 비밀번호 찾기
    Optional<MemberVO> findByEmpIdAndEmpNmAndEmpTel(String empId, String empNm, String empTel);
    // 로그인
    Optional<MemberVO> findByEmpIdAndEmpPw(String empId, String empPw);

}


