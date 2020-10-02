package com.rubycon.rubyconteam2.domain.party.domain;

import com.rubycon.rubyconteam2.domain.party.dto.request.PartyUpdateRequest;
import com.rubycon.rubyconteam2.global.common.model.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Party extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int leaderPrice;

    @Column(nullable = false)
    private int memberPrice;

    @Column
    private int memberCount;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private PaymentCycle paymentCycle;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private PartyState partyState;

    public void updateMyParty(PartyUpdateRequest partyDto){
        this.title = partyDto.getTitle();
        this.leaderPrice = partyDto.getLeaderPrice();
        this.memberPrice = partyDto.getMemberPrice();
        this.paymentCycle = partyDto.getPaymentCycle();
        this.partyState = partyDto.getPartyState();
    }

    public void plusMemberCount(){
        this.memberCount++;
    }

    public void minusMemberCount(){
        this.memberCount--;
    }
}
