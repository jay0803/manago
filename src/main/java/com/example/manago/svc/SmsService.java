package com.example.manago.svc;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final DefaultMessageService messageService;

    public SmsService() {
        this.messageService = NurigoApp.INSTANCE.initialize(
                "COOLSMS_API_KEY",
                "COOLSMS_API_SECRET",
                "https://api.coolsms.co.kr"
        );
    }

    public void sendSms(String to, String text) {
        Message message = new Message();
        message.setFrom("010-xxxx-xxxx"); // coolsms 등록 발신번호
        message.setTo(to);
        message.setText(text);

        try {
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException e) {
            e.printStackTrace(); // 에러 로그
            throw new RuntimeException("SMS 발송 실패", e); // 필요시
        } catch (NurigoEmptyResponseException e) {
            throw new RuntimeException(e);
        } catch (NurigoUnknownException e) {
            throw new RuntimeException(e);
        }
    }
}

