package com.example.healthfunctionalfood.domain.review;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customerReport")
public class CustomerReport extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerReport_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerReview customerReview;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Lob
    private String comment;
}
