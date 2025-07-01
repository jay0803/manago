package com.example.manago.controller;

import com.example.manago.svc.SmsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired private SmsService smsService;

    @PostMapping("/send")
    public Map<String, Object> sendSms(@RequestBody Map<String, String> payload, HttpSession session) {
        String to = payload.get("to");
        String code = String.valueOf((int)((Math.random()*90000)+10000)); // 5자리 랜덤
        try {
            smsService.sendSms(to, "[관리자시스템] 인증번호: " + code);
            session.setAttribute("smsCode", code);
            session.setMaxInactiveInterval(60*5); // 5분 제한
            return Map.of("success", true);
        } catch(Exception e) {
            return Map.of("success", false, "msg", e.getMessage());
        }
    }
}
