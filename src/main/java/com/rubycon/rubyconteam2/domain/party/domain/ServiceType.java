package com.rubycon.rubyconteam2.domain.party.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {
    NETFLIX("넷플릭스"),
    WATCHA("왓챠"),
    WAVVE( "웨이브"),
    APPLE_MUSIC( "애플 뮤직")
    ;

    private final String title;
}
