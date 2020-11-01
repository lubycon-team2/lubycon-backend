package com.rubycon.rubyconteam2.domain.party.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyState {
    RECRUITING("모집 중"),
    ADDITIONAL_RECRUITING("추가 모집 중"),
    COMPLETED("모집 완료"),
    DELETED("파티 삭제"),
    ;

    private final String title;

    public boolean isRecruiting(){
        return this.equals(PartyState.RECRUITING);
    }

    public boolean isAdditionalRecruiting(){
        return this.equals(PartyState.ADDITIONAL_RECRUITING);
    }

    public boolean isCompleted(){
        return this.equals(PartyState.COMPLETED);
    }

    public boolean isDeleted(){
        return this.equals(PartyState.DELETED);
    }

    public boolean isNotReviewable(){
        return this.isDeleted() || this.isRecruiting();
    }
}
