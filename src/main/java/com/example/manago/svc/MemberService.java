package com.example.manago.svc;

import com.example.manago.repository.MemberRepository;
import com.example.manago.vo.EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public EmpVO loginCheck(String empId, String empPw) {
        return memberRepository.findByEmpIdAndEmpPw(empId, empPw);
    }
    public EmpVO findByEmpNmAndEmpTel(String empNm, String empTel) {
        return memberRepository.findByEmpNmAndEmpTel(empNm, empTel);
    }
}
