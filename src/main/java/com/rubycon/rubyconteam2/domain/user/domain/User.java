package com.rubycon.rubyconteam2.domain.user.domain;

import com.rubycon.rubyconteam2.global.common.model.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique=true)
    private String oauthKey;

    @Column(nullable = false)
    private String name;

    @Column
    private String profileImage;

    @Column(nullable = false)
    private String providerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //    @Column(nullable = false)
    //    private String accessToken;

    @Builder
    public User(String name, String oauthKey, String profileImage, String providerType, Role role) {
        this.name = name;
        this.oauthKey = oauthKey;
        this.profileImage = profileImage;
        this.providerType = providerType;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
