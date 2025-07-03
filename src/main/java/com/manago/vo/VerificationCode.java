package com.manago.vo;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VerificationCode {
    // getter/setter 생략 (롬복 써도 됨)
    private String phoneNumber;
    private String code;
    private LocalDateTime createdAt;

    // 생성자
    public VerificationCode(String phoneNumber, String code, LocalDateTime createdAt) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.createdAt = createdAt;
    }
}

