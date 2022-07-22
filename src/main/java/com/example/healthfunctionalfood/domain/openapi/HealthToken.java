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

    @Builder
    public HealthToken(String cxId, User user, String reqTxId, String txId, String token) {
        this.cxId = cxId;
        this.user = user;
        this.reqTxId = reqTxId;
        this.txId = txId;
        this.token = token;
    }

    public void updateHealthToken(String cxId, String reqTxId, String token, String txId) {
        this.cxId = cxId;
        this.reqTxId = reqTxId;
        this.token = token;
        this.txId = txId;
    }
}
