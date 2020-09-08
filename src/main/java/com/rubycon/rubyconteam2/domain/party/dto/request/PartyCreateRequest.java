package com.rubycon.rubyconteam2.domain.party.dto.request;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PartyCreateRequest {

    private String title;
    private String description;
    private int maxPartyNumber;
    private int paymentCycle;
    private int pricePerPerson;
    private ServiceType serviceType;
    private PartyState partyState;

    public Party toEntity() {
        return Party.builder()
                .title(title)
                .description(description)
                .maxPartyNumber(maxPartyNumber)
                .paymentCycle(paymentCycle)
                .pricePerPerson(pricePerPerson)
                .serviceType(serviceType)
                .partyState(partyState)
                .build();
    }
}
