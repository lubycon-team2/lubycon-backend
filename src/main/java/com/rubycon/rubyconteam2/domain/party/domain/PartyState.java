package com.rubycon.rubyconteam2.domain.party.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyState {
    RECRUITING("파티 모집 중"),
    COMPLETED("파티 모집 완료"),
    DELETED("파티 삭제"),
    ;

    private final String title;

    public Boolean isRecruiting(){
        if (this.equals(PartyState.RECRUITING)) return Boolean.TRUE;
        else return Boolean.FALSE;
    }

    public Boolean isCompleted(){
        if (this.equals(PartyState.COMPLETED)) return Boolean.TRUE;
        else return Boolean.FALSE;
    }

    public Boolean isDeleted(){
        if (this.equals(PartyState.DELETED)) return Boolean.TRUE;
        else return Boolean.FALSE;
    }
}
