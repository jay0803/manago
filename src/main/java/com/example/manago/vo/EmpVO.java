package com.example.manago.vo;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "EMP")
public class EmpVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_NO")
    private Integer empNo;            // 사번 (PK)

    @Column(name = "EMP_NM")
    private String empNm;             // 이름

    @Column(name = "EMP_TEL")
    private String empTel;            // 전화번호

    @Column(name = "GENDER")
    private String gender;            // 성별

    @Column(name = "EMP_ID")
    private String empId;             // 아이디

    @Column(name = "EMP_PW")
    private String empPw;             // 비밀번호

    @Column(name = "EMP_EM")
    private String empEm;             // 이메일

    @Column(name = "DATE")
    private String date;              // 생년월일

    @Column(name = "POSITION")
    private String position;          // 직급

    @Column(name = "POSITION_NO")
    private Integer positionNo;       // 직급번호 (1=임원, 2=인사, 3=일반)

    @Column(name = "MANAGER_NO")
    private Integer managerNo;        // 직속상사 번호

    @Column(name = "DEPT_NO")
    private Integer deptNo;           // 부서번호 (FK)

    @Column(name = "HIREDATE")
    private Date hireDate;            // 입사일자

}
