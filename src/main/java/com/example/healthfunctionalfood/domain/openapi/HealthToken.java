package com.example.healthfunctionalfood.domain.openapi;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "healthToken")
public class HealthToken extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tilkoToken_id")
    private Long id;

    private String cxId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String reqTxId;

    private String txId;

    @Lob
    private String token;
}
