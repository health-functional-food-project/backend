package com.example.healthfunctionalfood.dto.response;

import com.example.healthfunctionalfood.domain.openapi.Diagnosis;
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
}
