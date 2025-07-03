package com.manago.controller;

import com.manago.service.SmsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send-sms")
    public Map<String, Object> sendSms(@RequestBody Map<String, String> payload, HttpSession session) {
        String tel = payload.get("tel");

        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        session.setAttribute("smsCode", code);
        session.setAttribute("smsTel", tel);
        session.setAttribute("smsTime", System.currentTimeMillis());

        smsService.sendSms(tel, "[인증번호] " + code + " 를 입력해주세요.");

        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }
}
