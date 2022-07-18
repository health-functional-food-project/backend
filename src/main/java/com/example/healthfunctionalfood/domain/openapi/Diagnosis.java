package com.example.healthfunctionalfood.domain.openapi;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.common.Address;
import com.example.healthfunctionalfood.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diagnosis")
public class Diagnosis extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    //병원 약국 명
    @Embedded
    private Address address;

    //진료 개시일
    private LocalDateTime treatmentStartDate;

    //투약(요양)회수
    private int dosingDay;

    //방문 일수
    private int visitCount;

    //진료형태
    private String typeOfTreatment;

    //처방회수
    private String numberOfPrescriptions;

    //의약품 코드
    private String drugCode;

    //처방약품명
    private String drugName;

    //성분명 정보
    private String ingredientInfo;

    //전문/일반
    private String professionalGeneral;

    //단일/복합
    private String singleCompound;

    //제조/수입사
    private String importer;

    //판매사
    private String vendor;

    //제형
    private String formulation;

    //투여경로
    private String routeOfAdministration;

    //복지부 분류
    private String ministryOfWelfareClassification;

    //ATC 정보
    private String atcInfo;

    //KPIC 악효분류
    @Lob
    private String kpicInfo;

    //효능 효과
    @Lob
    private String efficacyEffect;

    //용법 용량
    @Lob
    private String dosage;

    //주의 사항
    @Lob
    private String precautions;

    //복약 지도
    private String medicationGuidance;
}
