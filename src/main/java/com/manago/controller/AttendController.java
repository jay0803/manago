package com.manago.controller;

import com.manago.service.AttendService;
import com.manago.vo.Attend;
import com.manago.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Member;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class AttendController {

    private final AttendService attendService;

    // 로그인 후 공통 출퇴근 페이지
    @GetMapping("/attend")
    public String showCheckInOutPage(HttpSession session, Model model) {
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member == null) return "redirect:/login";

        int empNo = member.getEmpNo();
        Attend attend = attendService.getTodayAttend(empNo);

        model.addAttribute("emp", member);
        model.addAttribute("attend", attend);
        model.addAttribute("alreadyCheckedIn", attendService.hasCheckedInToday(empNo));
        model.addAttribute("alreadyCheckedOut", attendService.hasCheckedOutToday(empNo));
        model.addAttribute("today", LocalDate.now());

        return "checkinout";
    }


    // 출근 처리
    @PostMapping("/attend/checkin")
    public String checkIn(HttpSession session, RedirectAttributes redirectAttributes) {
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member != null) {
            attendService.checkIn(member.getEmpNo());
            redirectAttributes.addFlashAttribute("msg", "출근 완료되었습니다.");
            return "redirect:/index";
        }
        return "redirect:/login";
    }


    // 퇴근 처리
    @PostMapping("/attend/checkout")
    @ResponseBody
    public String checkOut(HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("loginUser");
        if (member != null) {
            attendService.checkOut(member.getEmpNo());
            return "redirect:/index";
        }
        return "redirect:/login";
    }
}
