package com.manago.repository;


import com.manago.vo.VerificationCode;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VerificationCodeRepository {
    private final Map<String, VerificationCode> repo = new ConcurrentHashMap<>();

    public void save(VerificationCode code) {
        repo.put(code.getPhoneNumber(), code);
    }

    public VerificationCode findByPhoneNumber(String phoneNumber) {
        return repo.get(phoneNumber);
    }

    public void delete(VerificationCode code) {
        repo.remove(code.getPhoneNumber());
    }
}
