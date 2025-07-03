package com.manago.vo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EMP")
public class MemberVO {

    //사번
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_NO")
    private int empNo;

    // 이름
    @Column(length = 100 ,name = "EMP_NM" ,nullable = false)
    private String empNm;

    // 전번
    @Column(length = 13 ,name = "EMP_TEL" ,nullable = false)
    private String empTel;

    // 성별
    @Column(length = 1 ,nullable = false)
    private String gender;

    // 아이디
    @Column(length = 20 ,name = "EMP_ID" ,nullable = false)
    private String empId;

    // 비번
    @Column(length = 16 ,name = "EMP_PW" ,nullable = false)
    private String empPw;

    // 이메일
    @Column(length = 30 ,name = "EMP_EM" ,nullable = false)
    private String empEm;

    // 주소
    @Column(length = 100 ,name = "ADDRESS" ,nullable = true)
    private String address; // 주소

    // 생년월일
    @Column(length = 10 ,nullable = false)
    private String date;

    // 직급
    @Column(name = "position", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'user'")
    private String position;


    // 직책 번호
    @Column(length = 20 ,name = "POSITION_NO")
    private Integer positionNo;

    // 부서 번호
    @ManyToOne(fetch = FetchType.LAZY)//fetch = FetchType.LAZY : 필요할 때만 데이터를 불러옴
    @JoinColumn(name = "DEPT_NO", nullable = true)
    private DepartmentVO deptNo;

    // 입사 일자
    @Column(name = "HIREDATE")
    private String hireDate;

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }
}
