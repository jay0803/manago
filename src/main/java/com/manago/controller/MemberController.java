package com.manago.controller;

import com.manago.service.MemberService;
import com.manago.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/find_id")
    public String findIdPage() {
        return "find_id";
    }


    @PostMapping("/find-id")
    public String findId(
            @RequestParam String empNm,
            @RequestParam String empTel,
            @RequestParam String smsCode,
            HttpSession session,
            Model model) {

        String savedCode = (String) session.getAttribute("smsCode");
        String savedTel = (String) session.getAttribute("smsTel");
        Long savedTime = (Long) session.getAttribute("smsTime");

        if (savedCode == null || savedTel == null || savedTime == null) {
            model.addAttribute("error", "인증번호가 없습니다. 다시 요청해주세요.");
            return "find_id";
        }
        if (System.currentTimeMillis() - savedTime > 5 * 60 * 1000) {
            model.addAttribute("error", "인증번호가 만료되었습니다.");
            return "find_id";
        }
        if (!savedTel.equals(empTel)) {
            model.addAttribute("error", "전화번호가 일치하지 않습니다.");
            return "find_id";
        }
        if (!savedCode.equals(smsCode)) {
            model.addAttribute("error", "인증번호가 일치하지 않습니다.");
            return "find_id";
        }

        MemberVO member = memberService.findByEmpNmAndEmpTel(empNm, empTel);
        if (member == null) {
            model.addAttribute("error", "일치하는 계정이 없습니다.");
            return "find_id";
        }

        model.addAttribute("foundId", member.getEmpId());
        return "find_id_success";
    }


    @GetMapping("/find_pw")
    public String findPwPage() {
        return "find_pw";
    }


    @PostMapping("/find-pw-proc")
    public String findPw(
            @RequestParam String empNm,
            @RequestParam String empId,
            @RequestParam String empTel,
            @RequestParam String smsCode,
            HttpSession session,
            Model model) {

        String savedCode = (String) session.getAttribute("smsCode");
        String savedTel = (String) session.getAttribute("smsTel");
        Long savedTime = (Long) session.getAttribute("smsTime");

        if (savedCode == null || savedTel == null || savedTime == null) {
            model.addAttribute("error", "인증번호가 없습니다. 다시 요청해주세요.");
            return "find_pw";
        }
        if (System.currentTimeMillis() - savedTime > 5 * 60 * 1000) {
            model.addAttribute("error", "인증번호가 만료되었습니다.");
            return "find_pw";
        }
        if (!savedTel.equals(empTel)) {
            model.addAttribute("error", "전화번호가 일치하지 않습니다.");
            return "find_pw";
        }
        if (!savedCode.equals(smsCode)) {
            model.addAttribute("error", "인증번호가 일치하지 않습니다.");
            return "find_pw";
        }

        MemberVO member = memberService.findByEmpIdAndEmpNmAndEmpTel(empId, empNm, empTel);
        if (member == null) {
            model.addAttribute("error", "일치하는 계정이 없습니다.");
            return "find_pw";
        }

        session.setAttribute("verifiedMemberNo", member.getEmpNo());
        return "redirect:/change-pw";
    }


    @GetMapping("/change-pw")
    public String changePwPage(HttpSession session, Model model) {
        if (session.getAttribute("verifiedMemberNo") == null) {
            model.addAttribute("error", "인증되지 않았습니다.");
            return "find_pw";
        }
        return "change_pw";
    }


    @PostMapping("/change-pw")
    public String changePw(
            @RequestParam String newPw,
            @RequestParam String confirmPw,
            HttpSession session,
            Model model) {

        Integer empNo = (Integer) session.getAttribute("verifiedMemberNo");
        if (empNo == null) {
            model.addAttribute("error", "인증되지 않았습니다.");
            return "find_pw";
        }

        if (!newPw.equals(confirmPw)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "change_pw";
        }

        MemberVO member = memberService.findById(empNo);
        if (member == null) {
            model.addAttribute("error", "계정을 찾을 수 없습니다.");
            return "change_pw";
        }

        member.setEmpPw(newPw);
        memberService.saveMember(member);
        session.removeAttribute("verifiedMemberNo");

        return "redirect:/login";
    }
}

