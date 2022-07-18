package com.example.healthfunctionalfood.domain.review;

import com.example.healthfunctionalfood.TimeStamped;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customerKeyword")
public class CustomerKeyword extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerKeyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerReview_id")
    private CustomerReview customerReview;

    private String keyword;
}
