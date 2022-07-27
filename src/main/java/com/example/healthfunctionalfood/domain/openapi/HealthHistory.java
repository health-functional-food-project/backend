package com.example.healthfunctionalfood.domain.openapi;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "healthHistory")
public class HealthHistory extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "healthHistory_id")
    private Long id;

    //건강검진 년도
    private int year;

    //검진일자
    private String checkUpDate;

    //결과 코드
    private String code;

    //진단 장소
    private String location;

    //설명
    @Lob
    private String description;

    private String kidney;
    private String weight;
    private String waistCircumference;
    private String bodyMassIndex;
    private String Vision;
    private String ear;
    private String BloodPressure;
    private String urineProtein;
    private String hemoglobin;
    private String fastingBloodSugar;
    private String totalCholesterol;
    private String HDLCholesterol;
    private String triglycerides;
    private String LDLCholesterol;
    private String serumCreatinine;
    private String Gfr;
    private String Sgot;
    private String Sgpt;
    private String yGtp;
    private String PTCD;
    private String osteoporosis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public HealthHistory(int year, String checkUpDate, String code, String location, String description, String kidney, String weight, String waistCircumference, String bodyMassIndex, String vision, String ear, String bloodPressure, String urineProtein, String hemoglobin, String fastingBloodSugar, String totalCholesterol, String HDLCholesterol, String triglycerides, String LDLCholesterol, String serumCreatinine, String gfr, String sgot, String sgpt, String yGtp, String PTCD, String osteoporosis, User user) {
        this.year = year;
        this.checkUpDate = checkUpDate;
        this.code = code;
        this.location = location;
        this.description = description;
        this.kidney = kidney;
        this.weight = weight;
        this.waistCircumference = waistCircumference;
        this.bodyMassIndex = bodyMassIndex;
        this.Vision = vision;
        this.ear = ear;
        this.BloodPressure = bloodPressure;
        this.urineProtein = urineProtein;
        this.hemoglobin = hemoglobin;
        this.fastingBloodSugar = fastingBloodSugar;
        this.totalCholesterol = totalCholesterol;
        this.HDLCholesterol = HDLCholesterol;
        this.triglycerides = triglycerides;
        this.LDLCholesterol = LDLCholesterol;
        this.serumCreatinine = serumCreatinine;
        this.Gfr = gfr;
        this.Sgot = sgot;
        this.Sgpt = sgpt;
        this.yGtp = yGtp;
        this.PTCD = PTCD;
        this.osteoporosis = osteoporosis;
        this.user = user;
    }
}
