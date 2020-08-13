package com.rubycon.rubyconteam2.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 스프링 시큐리티에서는 권한 코드에 항상 "ROLE_"이 앞에 있어야함

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "게스트"),
    USER("ROLE_USER", "사용자");

    private final String key;
    private final String title;
}
