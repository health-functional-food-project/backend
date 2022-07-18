package com.example.healthfunctionalfood.domain.review;


import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "expertReview")
public class ExpertReview extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expertReview_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String userName;

    private String pharmacy;

    private double starRating;

    private String comment;

    @OneToMany(mappedBy = "expertReview",cascade = CascadeType.ALL)
    private final List<ExpertKeyword> expertKeywords = new ArrayList<>();
}
