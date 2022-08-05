package com.example.healthfunctionalfood.domain.review;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customerLike")
public class CustomerLike extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerLike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerReview customerReview;

    @Builder
    public CustomerLike(User user, CustomerReview customerReview) {
        this.user = user;
        this.customerReview = customerReview;
    }
}
