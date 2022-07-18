package com.example.healthfunctionalfood.domain.openapi;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gender")
public class Gender extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gender_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate period;

    private String sectionUnit;

    private String keyWord;

    private String gender;

    private Double ratio;
}