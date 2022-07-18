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

    @OneToMany(mappedBy = "customerReview",cascade = CascadeType.ALL)
    private final List<CustomerKeyword> customerKeywords = new ArrayList<>();

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
}
