package com.manago.vo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DEPT")
public class DepartmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPT_NO")
    private int deptNo;
}
