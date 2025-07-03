package com.manago.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AdminAttendController {

//    private final MemberService memberService;
//    private final AttendService attendService;
//
//    // 부서별 버튼 클릭 시 해당 부서 사원들의 출결 정보를 보여주는 화면
//    @GetMapping("/admin/attend/{deptNo}")
//    public String viewAttendByDept(@PathVariable int deptNo, Model model) {
//        // 해당 부서 사원 목록 불러오기
//        List<MemberVO> memberList = memberService.getMembersByDeptNo(deptNo);
//
//        // 사원별 출결 리스트 Map<empNo, List<Attend>>
//        Map<Integer, List<Attend>> attendMap = new HashMap<>();
//        for (MemberVO member : memberList) {
//            List<Attend> attendList = attendService.getAttendByEmpNo(member.getEmpNo());
//            attendMap.put(member.getEmpNo(), attendList);
//        }
//
//        model.addAttribute("memberList", memberList);
//        model.addAttribute("attendMap", attendMap);
//        return "admin/attend_manage_detail"; // 부서 사원 출결관리 HTML
//    }
//}
}