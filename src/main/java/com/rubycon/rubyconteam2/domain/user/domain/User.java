package com.rubycon.rubyconteam2.domain.user.domain;

import com.rubycon.rubyconteam2.common.model.BaseTimeEntity;
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

    @Column(nullable = false)
    private String oauthKey;

    @Column(nullable = false)
    private String name;

    @Column
    private String profile_image;

    @Column(nullable = false)
    private String providerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //    @Column(nullable = false)
    //    private String accessToken;

    @Builder
    public User(String name, String oauthKey, String profile_image, String providerName, Role role) {
        this.name = name;
        this.oauthKey = oauthKey;
        this.profile_image = profile_image;
        this.providerName = providerName;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
