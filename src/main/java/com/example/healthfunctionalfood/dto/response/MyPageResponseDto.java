package com.example.healthfunctionalfood.dto.response;

import com.example.healthfunctionalfood.domain.openapi.Diagnosis;
import com.example.healthfunctionalfood.domain.openapi.HealthHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MyPageResponseDto {

    @NoArgsConstructor
    @Getter
    public static class Treatment{
        private Long dianosisId;

        private LocalDateTime treatmentStartDate;

        private String address;

        private String drugName;

        private String ministryOfWelfare;

        private int dosingDay;

        public Treatment(Diagnosis diagnosis){
            this.dianosisId = diagnosis.getId();
            this.treatmentStartDate = diagnosis.getTreatmentStartDate();
            this.address = diagnosis.getAddress().getAddress();
            this.drugName = diagnosis.getDrugName();
            this.ministryOfWelfare = diagnosis.getMinistryOfWelfareClassification();
            this.dosingDay = diagnosis.getDosingDay();

        }
    }

    @Getter
    @NoArgsConstructor
    public static class health {

        private Long health_id;
        //건강검진 년도

        private int year;
        //검진일자

        private String checkUpDate;
        //결과 코드

        private String code;
        //진단 장소

        private String location;
        //설명

        private String description;
        private String kidney;
        private String weight;
        private String bodyMassIndex;
        private String waistCircumference;
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

        public health(HealthHistory healthHistory) {
            this.health_id = healthHistory.getId();
            this.year = healthHistory.getYear();
            this.checkUpDate = healthHistory.getCheckUpDate();
            this.code = healthHistory.getCode();
            this.location = healthHistory.getLocation();
            this.description = healthHistory.getDescription();
            this.kidney = healthHistory.getKidney();
            this.weight = healthHistory.getWeight();
            this.bodyMassIndex = healthHistory.getBodyMassIndex();
            this.waistCircumference = healthHistory.getWaistCircumference();
            this.Vision = healthHistory.getVision();
            this.ear = healthHistory.getEar();
            this.BloodPressure = healthHistory.getBloodPressure();
            this.urineProtein = healthHistory.getUrineProtein();
            this.hemoglobin = healthHistory.getHemoglobin();
            this.fastingBloodSugar = healthHistory.getFastingBloodSugar();
            this.totalCholesterol = healthHistory.getTotalCholesterol();
            this.HDLCholesterol = healthHistory.getHDLCholesterol();
            this.triglycerides = healthHistory.getTriglycerides();
            this.LDLCholesterol = healthHistory.getLDLCholesterol();
            this.serumCreatinine = healthHistory.getSerumCreatinine();
            this.Gfr = healthHistory.getGfr();
            this.Sgot = healthHistory.getSgot();
            this.Sgpt = healthHistory.getSgpt();
            this.yGtp = healthHistory.getYGtp();
            this.PTCD = healthHistory.getPTCD();
            this.osteoporosis = healthHistory.getOsteoporosis();
        }
    }
}
