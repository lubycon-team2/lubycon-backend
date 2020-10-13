package com.rubycon.rubyconteam2.domain.party.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyState {
    PROCEEDING("파티 진행 중"),
    END("파티 종료")
    ;

    private final String title;

    public Boolean isProceeding(){
        if (this.equals(PartyState.PROCEEDING)) return Boolean.TRUE;
        else return Boolean.FALSE;
    }

    public Boolean isEnd(){
        if (this.equals(PartyState.END)) return Boolean.TRUE;
        else return Boolean.FALSE;
    }
}
