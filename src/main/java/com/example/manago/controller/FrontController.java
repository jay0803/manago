package com.example.manago.controller;

import com.example.manago.svc.MemberService;
import com.example.manago.svc.SmsService;
import com.example.manago.vo.EmpVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FrontController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SmsService smsService;

    // 메인 페이지
    @GetMapping({"/", "/index"})
    public String index() { return "index"; }

    // 로그인 페이지
    @GetMapping("/login")
    public String login() { return "login"; }

    // 로그인 처리
    @PostMapping("/login")
    public String loginProc(@RequestParam String empId,
                            @RequestParam String empPw,
                            HttpSession session,
                            Model model) {
        EmpVO loginEmp = memberService.loginCheck(empId, empPw);
        if (loginEmp != null) {
            // 권한별로 positionNo 사용!
            session.setAttribute("loginEmp", loginEmp);
            session.setAttribute("positionNo", loginEmp.getPositionNo());
            return "redirect:/index";
        } else {
            model.addAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login";
        }
    }

    // 회원가입 페이지
    @GetMapping("/join")
    public String join() { return "join"; }

    // 아이디 찾기 페이지
    @GetMapping("/find-id")
    public String findIdPage() { return "find_id"; }

    // 아이디 찾기 처리
    @PostMapping("/find-id")
    public String findIdProc(@RequestParam String empNm,
                             @RequestParam String empTel,
                             @RequestParam String smsCode,
                             HttpSession session,
                             Model model) {
        // 예시: 인증번호 검증 및 실패/만료 처리 코드 필요 (위 참고)
        EmpVO user = memberService.findByEmpNmAndEmpTel(empNm, empTel);
        if (user == null) {
            model.addAttribute("msg", "일치하는 사용자가 없습니다.");
            return "find_id";
        }
        model.addAttribute("findEmpId", user.getEmpId());
        return "find_id_result";
    }
    // 비밀번호 찾기 페이지
    @GetMapping("/find-pw")
    public String findPwPage() {
        return "find_pw"; // templates/find_pw.html로 연결!
    }

    // (추후) 비밀번호 찾기 처리도 아래처럼 추가 가능
    @PostMapping("/find-pw")
    public String findPwProc(@RequestParam String empId,
                             @RequestParam String empEm,
                             @RequestParam String smsCode,
                             Model model) {
        // 여기에 인증번호 검증, 회원 찾기, 임시비번 발급/메일 발송 등 구현
        // 예시:
        // EmpVO user = memberService.findByEmpIdAndEmpEm(empId, empEm);
        // if (user == null) {
        //     model.addAttribute("msg", "일치하는 사용자가 없습니다.");
        //     return "find_pw";
        // }
        // model.addAttribute("findPwResult", "임시비밀번호가 전송되었습니다!");
        // return "find_pw_result";
        return "find_pw"; // 임시
    }
    // 마이페이지 - 로그인한 사용자 정보 표시
    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        EmpVO loginEmp = (EmpVO) session.getAttribute("loginEmp");
        if (loginEmp == null) {
            return "redirect:/login"; // 로그인 안 했으면 로그인 페이지로 이동
        }
        model.addAttribute("emp", loginEmp);
        return "mypage";
    }
}
