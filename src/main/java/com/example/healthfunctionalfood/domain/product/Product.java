package com.example.healthfunctionalfood.domain.product;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.openapi.DomesticProduct;
import com.example.healthfunctionalfood.domain.openapi.ForeignProduct;
import com.example.healthfunctionalfood.domain.openapi.Age;
import com.example.healthfunctionalfood.domain.openapi.Gender;
import com.example.healthfunctionalfood.domain.common.Image;
import com.example.healthfunctionalfood.domain.review.ExpertReview;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Embedded
    private Image image;

    private String productName;

    private String companyName;

    private String funcContent;

    private String funcRawMaterials;

    private String precautions;

    private String importer;

    private String expirationDate;

    private String precautionsDetails;

    private String funcContentDetails;

    private String funcRawMaterialsDetails;

    private String otherRawMaterials;

    private int likeCount;

    private int viewCount;

    private String reportNumber;

    private String etc;

    private String primaryIngredients;

    private Double customerReviewAvg;

    private Double expertReviewAvg;


    @Lob
    private String naverReview;

    @OneToOne
    @JoinColumn(name = "domesticProduct_id")
    private DomesticProduct domesticProduct;

    @OneToOne
    @JoinColumn(name = "foreignProduct_id")
    private ForeignProduct foreignProduct;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<Age> ageList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<Gender> genderList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<ExpertReview> expertReviewList = new ArrayList<>();

    public void updateExpertReviewAge(double expertReviewAvg) {
        this.expertReviewAvg = expertReviewAvg;
    }
}