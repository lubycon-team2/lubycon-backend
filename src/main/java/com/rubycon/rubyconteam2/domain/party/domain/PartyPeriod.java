package com.rubycon.rubyconteam2.domain.party.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyPeriod {
    MONTH_1("1개월"),
    MONTH_3("3개월"),
    MONTH_6("6개월"),
    YEAR_1("1년")
    ;

    private final String period;
}
