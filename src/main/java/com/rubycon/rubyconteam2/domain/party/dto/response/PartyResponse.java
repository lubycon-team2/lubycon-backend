package com.rubycon.rubyconteam2.domain.party.dto.response;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.domain.PaymentCycle;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PartyResponse {

    private Long partyId;
    private String title;
    private int leaderPrice;
    private int memberPrice;
    private int memberCount;
    private PaymentCycle paymentCycle;
    private ServiceType serviceType;
    private PartyState partyState;

    public PartyResponse(Party party) {
        this.partyId = party.getPartyId();
        this.title = party.getTitle();
        this.leaderPrice = party.getLeaderPrice();
        this.memberPrice = party.getMemberPrice();
        this.memberCount = party.getMemberCount();
        this.paymentCycle = party.getPaymentCycle();
        this.serviceType = party.getServiceType();
        this.partyState = party.getPartyState();
    }
}
