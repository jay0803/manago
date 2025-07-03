package com.manago.controller;

import com.manago.vo.MemberVO;
import com.manago.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class MyPageController {
    @Autowired
    private MemberService memberService;

    // 역할 변환 함수는 여기에 있어도 무방
    private String getRoleFromPosition(Integer positionNo) {
        if (positionNo == null) return "user";
        return switch (positionNo) {
            case 1 -> "admin";
            case 2 -> "super";
            default -> "user";
        };
    }

    @GetMapping("/myInfo")
    public String myInfo(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";
        model.addAttribute("user", loginUser);
        return "myInfo";
    }

    @PostMapping("/myInfoUpdate")
    @ResponseBody
    public String updateInfoJson(@RequestBody MemberVO user, HttpSession session) {
        MemberVO origin = memberService.findByEmpId(user.getEmpId());
        if (origin == null) {
            return "fail";
        }

        // null값 들어오는 필드 기존 값으로 채워줌 (이거 안하면 NOT NULL 컬럼 에러 터짐)
        if (user.getEmpNm() == null) user.setEmpNm(origin.getEmpNm());
        if (user.getEmpTel() == null) user.setEmpTel(origin.getEmpTel());
        if (user.getEmpPw() == null) user.setEmpPw(origin.getEmpPw());
        if (user.getEmpEm() == null) user.setEmpEm(origin.getEmpEm());
        if (user.getAddress() == null) user.setAddress(origin.getAddress());
        if (user.getGender() == null) user.setGender(origin.getGender()); // ✅ gender 추가
        if (user.getHireDate() == null) user.setHireDate(origin.getHireDate());
        if (user.getDate() == null) user.setDate(origin.getDate());
        if (user.getPosition() == null) user.setPosition(origin.getPosition());
        if (user.getPositionNo() == null && origin.getPositionNo() != null) user.setPositionNo(origin.getPositionNo());
        if (user.getDeptNo() == null) user.setDeptNo(origin.getDeptNo());

        memberService.updateMember(user);
        return "success";
    }

}