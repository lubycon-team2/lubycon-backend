package com.rubycon.rubyconteam2.domain.party.dto.response;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.domain.PaymentCycle;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PartyResponse {

    @ApiModelProperty(value = "파티 ID", required = true, example = "1")
    private Long partyId;

    @ApiModelProperty(value = "파티 제목", required = true, example = "넷플릭스 파티 모집")
    private String title;

    @ApiModelProperty(value = "파티장 금액", required = true, example = "3600")
    private int leaderPrice;

    @ApiModelProperty(value = "파티원 금액", required = true, example = "3400")
    private int memberPrice;

    @ApiModelProperty(value = "현재 파티원 수", required = true, example = "2")
    private int memberCount;

    @ApiModelProperty(value = "현재 서비스의 최대 파티원 수", required = true, example = "4")
    private int maxMemberCount;

    @ApiModelProperty(value = "결제 주기", required = true, example = "MONTH_1 | MONTH_2 | MONTH_6 | YEAR_1")
    private PaymentCycle paymentCycle;

    @ApiModelProperty(value = "서비스 타입", required = true, example = "NETFLIX | WATCHA | WAAVE | APPLE_MUSIC")
    private ServiceType serviceType;

    @ApiModelProperty(value = "현재 파티 상태", required = true, example = "RECRUITING | START | END")
    private PartyState partyState;

    public PartyResponse(Party party) {
        this.partyId = party.getPartyId();
        this.title = party.getTitle();
        this.leaderPrice = party.getLeaderPrice();
        this.memberPrice = party.getMemberPrice();
        this.memberCount = party.getMemberCount();
        this.maxMemberCount = party.getServiceType().getMaxSharedAccountsCount();
        this.paymentCycle = party.getPaymentCycle();
        this.serviceType = party.getServiceType();
        this.partyState = party.getPartyState();
    }
}
