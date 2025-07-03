package com.manago.controller;

import com.manago.service.MemberService;
import com.manago.service.SmsService;
import com.manago.util.RedisUtil;
import com.manago.vo.DepartmentVO;
import jakarta.servlet.http.HttpSession;
import com.manago.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Random;
import com.manago.repository.DepartmentRepository;

@Controller
public class FrontController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginProc(@RequestParam String empId,
                            @RequestParam String empPw,
                            HttpSession session,
                            Model model) {
        MemberVO loginUser = memberService.loginCheck(empId, empPw);

        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            session.setAttribute("position", loginUser.getPosition());
            return "redirect:/index";
        } else {
            model.addAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/join")
    public String join() {
        return "join";  // join.html 반환
    }

    @PostMapping("/join")
    public String joinProc(@ModelAttribute MemberVO memberVO, Model model) {
        if (memberVO.getPosition() == null || memberVO.getPosition().isBlank()) {
            memberVO.setPosition("user");
        }
        // 회원가입 저장 처리
        memberService.saveMember(memberVO); // saveMember 메서드는 Service에 만들어 둬야 해
        model.addAttribute("msg", "회원가입이 완료되었습니다.");
        return "login"; // 가입 후 로그인 페이지로 이동 (원하면 redirect:/login 가능)
    }

}

