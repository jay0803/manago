package com.manago.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.manago.repository.MemberRepository;
import com.manago.vo.MemberVO;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 🔵 사원 ID 찾기
    public Optional<String> findUserId(String empNm, String date, String empTel) {
        return memberRepository.findByEmpNmAndDateAndEmpTel(empNm, date, empTel)
                .map(MemberVO::getEmpId);
    }

    // 🔵 사원 비밀번호 찾기
    public Optional<String> findUserPw(String empId, String empNm, String empTel) {
        return memberRepository.findByEmpIdAndEmpNmAndEmpTel(empId, empNm, empTel)
                .map(MemberVO::getEmpPw);
    }

    // 🔵 로그인 체크
    public MemberVO loginCheck(String empId, String empPw) {
        return memberRepository.findByEmpIdAndEmpPw(empId, empPw).orElse(null);
    }

    // 🔵 전체 사원 목록 조회 (추가)
    public List<MemberVO> getAllMembers() {
        return memberRepository.findAll();
    }

    //사번으로 조회
    public MemberVO findById(Integer empNo) {
        return memberRepository.findById(empNo).orElse(null);
    }

    // 🔵 사원 등록 (추가)
    public MemberVO saveMember(MemberVO memberVO) {
        return memberRepository.save(memberVO);
    }

    public MemberVO register(MemberVO memberVO) {
        System.out.println("service: " + memberVO);
        return memberRepository.save(memberVO);
    }

    // ID로 회원 조회
    public MemberVO findByEmpId(String empId) {
        return memberRepository.findByEmpId(empId).orElse(null);
    }

    // ID + PW로 로그인용 조회
    public MemberVO findByEmpIdAndEmpPw(String empId, String empPw) {
        return memberRepository.findByEmpIdAndEmpPw(empId, empPw).orElse(null);
    }

    // 이름 + 전화번호로 회원 찾기 (ID 찾기 등)
    public MemberVO findByEmpNmAndEmpTel(String empNm, String empTel) {
        return memberRepository.findByEmpNmAndEmpTel(empNm, empTel).orElse(null);
    }

    //아이디 + 이름 + 전화번호로 비밀번호 찾기
    public MemberVO findByEmpIdAndEmpNmAndEmpTel(String empId, String empNm, String empTel) {
        return memberRepository.findByEmpIdAndEmpNmAndEmpTel(empId, empNm, empTel).orElse(null);
    }

    // 회원 정보 수정 (empId 기준으로 기존 정보 찾아서 일부 필드만 수정)
    public void updateMember(MemberVO updated) {
        Optional<MemberVO> optional = memberRepository.findByEmpId(updated.getEmpId());
        if (optional.isPresent()) {
            MemberVO origin = optional.get();

            // 수정할 항목만 덮어쓰기
            if (updated.getEmpPw() != null) origin.setEmpPw(updated.getEmpPw());
            if (updated.getEmpEm() != null) origin.setEmpEm(updated.getEmpEm());
            if (updated.getEmpTel() != null) origin.setEmpTel(updated.getEmpTel());
            if (updated.getAddress() != null) origin.setAddress(updated.getAddress());
            if (updated.getGender() != null) origin.setGender(updated.getGender());
            if (updated.getHireDate() != null) origin.setHireDate(updated.getHireDate());
            if (updated.getDate() != null) origin.setDate(updated.getDate());
            if (updated.getPosition() != null) origin.setPosition(updated.getPosition());
            if (updated.getPositionNo() != null) origin.setPositionNo(updated.getPositionNo());
            if (updated.getDeptNo() != null) origin.setDeptNo(updated.getDeptNo());

            // 저장
            memberRepository.save(origin);
        }
    }

    // 신규 회원 저장 (선택)
    public void save(MemberVO member) {
        memberRepository.save(member);
    }
}


