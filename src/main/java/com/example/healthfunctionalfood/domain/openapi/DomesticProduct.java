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
@Table(name = "domesticProduct")
public class DomesticProduct extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domesticProduct_id")
    private Long id;

    //회사명
    private String companyName;

    //제품명
    private String productName;

    //기능성내용
    private String funcContent;

    //기능성원료
    private String funcRawMaterials;

    //제품 섭취 시 주의사항
    private String precautions;

    //신고번호
    private String reportNumber;

    //등록일자
    private String registrationDate;

    //유통기한
    private String expirationDate;

    //성상
    @Lob
    private String appearance;

    //섭취량 / 섭취방법
    @Lob
    private String intake;

    //포장재질(방법)
    @Lob
    private String packagingMaterial;

    //보존 및 유통기준
    @Lob
    private String preservation;

    //섭취 시 주의사항 상세
    @Lob
    private String precautionsDetails;

    //기능성 내용 상세
    @Lob
    private String funcContentDetails;

    //기준 및 규격
    @Lob
    private String standard;

    //기능성 원료 상세
    private String funcRawMaterialsDetails;

    //기타 원료
    @Lob
    private String otherRawMaterials;

    //캡슐 원료
    private String capsuleRawMaterial;

    @Embedded
    private Image image;
}
