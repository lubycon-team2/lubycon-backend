package com.rubycon.rubyconteam2.domain.party.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyState {
    RECRUITING("파티 모집 중"),
    START("파티 시작"),
    END("파티 종료")
    ;

    private final String title;
}
