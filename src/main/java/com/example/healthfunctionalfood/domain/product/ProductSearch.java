package com.example.healthfunctionalfood.domain.product;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.common.Image;
import com.example.healthfunctionalfood.domain.openapi.Age;
import com.example.healthfunctionalfood.domain.openapi.DomesticProduct;
import com.example.healthfunctionalfood.domain.openapi.ForeignProduct;
import com.example.healthfunctionalfood.domain.openapi.Gender;
import com.example.healthfunctionalfood.domain.review.ExpertReview;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "productSearch", createIndex = false, versionType = Document.VersionType.EXTERNAL)
@Builder
@AllArgsConstructor
public class ProductSearch extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_search_id")
    private Long id;

    @Field(type = FieldType.Keyword)
    private String productName;

    @Field(type = FieldType.Keyword)
    private String companyName;

    @Field(type = FieldType.Keyword)
    private String funcContent;

    @Field(type = FieldType.Keyword)
    private String funcRawMaterials;

    @Field(type = FieldType.Keyword)
    private String precautions;

    @Field(type = FieldType.Keyword)
    private String importer;

    @Field(type = FieldType.Keyword)
    private String expirationDate;

    @Field(type = FieldType.Keyword)
    private String precautionsDetails;

    @Field(type = FieldType.Keyword)
    private String funcContentDetails;

    @Field(type = FieldType.Keyword)
    private String funcRawMaterialsDetails;

    @Field(type = FieldType.Keyword)
    private String otherRawMaterials;

    @Field(type = FieldType.Long)
    private int likeCount;

    @Field(type = FieldType.Long)
    private int viewCount;

    @Field(type = FieldType.Keyword)
    private String reportNumber;

    @Field(type = FieldType.Keyword)
    private String etc;

    @Field(type = FieldType.Keyword)
    private String primaryIngredients;

    @Field(type = FieldType.Long)
    private Double customerReviewAvg;

    @Field(type = FieldType.Long)
    private Double expertReviewAvg;

    @Field(type = FieldType.Keyword)
    private String naverReview;
}