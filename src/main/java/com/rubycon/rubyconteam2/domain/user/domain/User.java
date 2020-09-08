package com.rubycon.rubyconteam2.domain.user.domain;

import com.rubycon.rubyconteam2.global.common.model.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 45)
    private String name;

    @Column
    private String email;

    @Column(length = 45)
    private String phoneNumber;

    @Column
    private String profileUrl;

    @Column(nullable = false, unique=true)
    private String oauthKey;

    @Column(nullable = false, length = 45)
    private String providerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public String getRoleKey() {
        return this.role.getKey();
    }

}
