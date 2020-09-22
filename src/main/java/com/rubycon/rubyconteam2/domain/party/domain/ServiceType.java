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
//    // 회의 후 최대 파티 인원 수 정리하기
//    private final int maxServiceNumber;
}
