package com.example.healthfunctionalfood.domain.review;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.product.Product;
import com.example.healthfunctionalfood.domain.user.User;
import com.example.healthfunctionalfood.dto.CustomerReviewDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customerReview")
public class CustomerReview extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerReview_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String keyword;

//    @OneToMany(mappedBy = "customerReview",cascade = CascadeType.ALL)
//    private final List<CustomerKeyword> customerKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "customerReview", cascade = CascadeType.ALL)
    private final List<CustomerLike> customerLikes = new ArrayList<>();


    private Integer starRating;

    private Boolean takingCheck;

    private Boolean familyTakingCheck;

    @Lob
    private String pros;

    @Lob
    private String cons;

    private Boolean exposureStatus;

    @Builder
    public CustomerReview(User user, Product product, Integer starRating, Boolean takingCheck, Boolean familyTakingCheck, String pros, String cons, Boolean exposureStatus, String keyword) {
        this.user = user;
        this.product = product;
        this.starRating = starRating;
        this.takingCheck = takingCheck;
        this.familyTakingCheck = familyTakingCheck;
        this.pros = pros;
        this.cons = cons;
        this.exposureStatus = exposureStatus;
        this.keyword = keyword;
    }

    public void updateReview(CustomerReviewDto.CreateReview updateReview) {
        this.keyword = updateReview.getKeyword();
        this.starRating = updateReview.getStarRating();
        this.takingCheck = updateReview.getTakingCheck();
        this.familyTakingCheck = updateReview.getFamilyTakingCheck();
        this.pros = updateReview.getPros();
        this.cons = updateReview.getCons();
        this.exposureStatus = updateReview.getExposureStatus();
    }
}
