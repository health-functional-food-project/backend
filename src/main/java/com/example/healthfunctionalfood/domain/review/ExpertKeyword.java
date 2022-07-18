package com.example.healthfunctionalfood.domain.review;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.common.ProsConsCaution;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "expertKeyword")
public class ExpertKeyword extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expertKeyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expertReview_id")
    private ExpertReview expertReview;

    @Enumerated(value = EnumType.STRING)
    private ProsConsCaution prosConsCaution;

    @Lob
    private String word;
}
