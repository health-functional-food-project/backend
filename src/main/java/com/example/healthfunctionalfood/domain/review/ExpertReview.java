package com.example.healthfunctionalfood.domain.review;


import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.request.ExpertReviewRequestDto;
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

    private String drugStore;

    private double starRating;

    private String comment;

    private String cons;

    private String pros;

    private String precautions;

    @Builder
    public ExpertReview(Product product, User user, String drugStore, double starRating, String comment, String cons, String pros, String precautions) {
        this.product = product;
        this.user = user;
        this.drugStore = drugStore;
        this.starRating = starRating;
        this.comment = comment;
        this.cons = cons;
        this.pros = pros;
        this.precautions = precautions;
    }

    public void updateExpertReview(ExpertReviewRequestDto.CreateReview createReview) {
        this.drugStore = createReview.getDrugStore();
        this.starRating = createReview.getStarRating();
        this.comment = createReview.getComment();
        this.cons = createReview.getCons();
        this.pros = createReview.getPros();
        this.precautions = createReview.getPrecautions();
    }
}
