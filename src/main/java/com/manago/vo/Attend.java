package com.manago.vo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attend")
@Getter
@Setter
public class Attend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendId;

    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    private LocalDate workDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}

