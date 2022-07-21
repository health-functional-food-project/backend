package com.example.healthfunctionalfood.domain.user;

import com.example.healthfunctionalfood.TimeStamped;
import com.example.healthfunctionalfood.domain.openapi.Diagnosis;
import com.example.healthfunctionalfood.domain.openapi.HealthHistory;
import com.example.healthfunctionalfood.domain.common.Image;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String kakaoId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String userName;

    private String nickname;

    private String password;

    @Embedded
    private Image image;

    private String email;

    private String gender;

    private int age;

    private String phone;

    private Boolean plusfriends;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Diagnosis> diagnosisList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<HealthHistory> healthHistories = new ArrayList<>();

    public static User OauthSignUp(String providerId, String email, String password, String name, String nickname, String age, String gender, String phone, Boolean plusfriends,
                                   SocialType socialType) {
        return new User(providerId, email, password, name, nickname, age, gender, phone, plusfriends, socialType);
    }

    private User(String kakaoId, String email, String password, String name, String nickname, String age, String gender, String phone, Boolean plusfriends,
                 SocialType socialType) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.password = password;
        this.userName = name;
        this.nickname = nickname;
        this.socialType = socialType;
        this.age = 0;
        this.gender = gender;
        this.phone = phone;
        this.plusfriends = plusfriends;
        this.role = Role.USER;

    }
}