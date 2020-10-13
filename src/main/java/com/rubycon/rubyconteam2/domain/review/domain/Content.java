package com.rubycon.rubyconteam2.domain.review.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Content {

    // 칭찬
    TYPE_C_1("친절하고 매너가 좋아요.", ContentType.COMPLIMENT),
    TYPE_C_2("책임감 있는 파티장이에요.", ContentType.COMPLIMENT),
    TYPE_C_3("답변이 빨라요.", ContentType.COMPLIMENT),
    TYPE_C_4("금액을 공평하게 설정했어요.", ContentType.COMPLIMENT),
    TYPE_C_5("정해진 입금 날짜를 잘 지켜요.", ContentType.COMPLIMENT),

    // 개선점
    TYPE_I_1("불친절해요.", ContentType.IMPROVEMENTS),
    TYPE_I_2("금액을 불공평하게 설정했어요.", ContentType.IMPROVEMENTS),
    TYPE_I_3("게시한 가격과 다르게 돈을 받아요.", ContentType.IMPROVEMENTS),
    TYPE_I_4("반말을 사용해요.", ContentType.IMPROVEMENTS),
    TYPE_I_5("단순 변심으로 파티를 종료해요.", ContentType.IMPROVEMENTS),
    TYPE_I_6("단순 변심으로 파티를 탈퇴해요.", ContentType.IMPROVEMENTS),
    TYPE_I_7("답변이 느려요.", ContentType.IMPROVEMENTS),
    TYPE_I_8("입금 기한에 늦었어요.", ContentType.IMPROVEMENTS),
    TYPE_I_9("답변이 느려요.", ContentType.IMPROVEMENTS),
    TYPE_I_10("입금하지 않고 서비스를 사용하려고 해요.", ContentType.IMPROVEMENTS),

    ;

    private final String title;
    private final ContentType contentType;
}
