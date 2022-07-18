package com.example.healthfunctionalfood.domain.openapi;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.common.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "foreignProduct")
public class ForeignProduct extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foreignProduct_id")
    private Long id;

    //수입업체
    private String importer;

    //제품 영문명
    private String productEnName;

    //제품 한글명
    private String productKoName;

    //기능성 내용
    private String funcContent;

    //기능성 원료
    private String funcRawMaterials;

    //제품 섭취 시 주의사항
    @Lob
    private String precautions;

    //회사명
    private String companyName;

    //제조국 / 수출국
    private String countryOfManufacture;

    //신고필증발급일자
    private String reportCertificateDate;

    //유통기한
    private String expirationDate;

    //섭취시 주의사항 상세
    @Lob
    private String precautionsDetails;

    //기능성 내용 상세
    @Lob
    private String funcContentDetails;

    //기능성 원료 상세
    private String funcRawMaterialsDetails;

    //기타 원료
    @Lob
    private String otherRawMaterials;

    @Embedded
    private Image image;
}
