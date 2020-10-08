package com.rubycon.rubyconteam2.domain.party.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    LEADER("ROLE_LEADER", "파티장"),
    MEMBER("ROLE_MEMBER", "파티원");

    private final String key;
    private final String title;

    public Boolean deleteAuthorization(){
        if (this.equals(Role.MEMBER)) return Boolean.FALSE;
        else return Boolean.TRUE;
    }
}
