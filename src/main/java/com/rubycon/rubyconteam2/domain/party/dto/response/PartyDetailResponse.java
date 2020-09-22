package com.rubycon.rubyconteam2.domain.party.dto.response;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PartyDetailResponse {

    private Long partyId;
    private String title;
    private String description;
    private int maxPartyNumber;
    private int paymentCycle;
    private int pricePerPerson;
    private ServiceType serviceType;
    private PartyState partyState;

    public PartyDetailResponse(Party party) {
        this.partyId = party.getPartyId();
        this.title = party.getTitle();
        this.description = party.getDescription();
        this.maxPartyNumber = party.getMaxPartyNumber();
        this.paymentCycle = party.getPaymentCycle();
        this.pricePerPerson = party.getPricePerPerson();
        this.serviceType = party.getServiceType();
        this.partyState = party.getPartyState();
    }
}