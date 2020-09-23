package com.rubycon.rubyconteam2.domain.party.dto.request;

import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.domain.PaymentCycle;
import com.rubycon.rubyconteam2.global.common.model.ValueOfEnum;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PartyUpdateRequest {

    @NotEmpty(message = "파티 제목을 입력해주세요.")
    private String title;

    @NotNull(message = "파티장 금액을 입력해주세요.")
    @Min(0)
    private Integer leaderPrice;

    @NotNull(message = "파티원 금액을 입력해주세요.")
    @Min(0)
    private Integer memberPrice;

    @NotEmpty(message = "결제 주기를 입력해주세요 \nMONTH_1 | MONTH_2 | MONTH_6 | YEAR_1")
    @ValueOfEnum(enumClass = PaymentCycle.class)
    private String paymentCycle;

    @NotEmpty(message = "파티 상태를 입력해주세요 \nRECRUITING | START | END")
    @ValueOfEnum(enumClass = PartyState.class)
    private String partyState;

    public PaymentCycle getPaymentCycle() {
        return PaymentCycle.valueOf(paymentCycle);
    }

    public PartyState getPartyState() {
        return PartyState.valueOf(partyState);
    }
}

