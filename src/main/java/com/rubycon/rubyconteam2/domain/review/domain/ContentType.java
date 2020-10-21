package com.rubycon.rubyconteam2.domain.review.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    COMPLIMENTS("칭찬"),
    IMPROVEMENTS("개선점")
    ;

    private final String title;
}
