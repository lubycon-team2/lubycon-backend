package com.rubycon.rubyconteam2.domain.party.domain;

import com.rubycon.rubyconteam2.global.common.model.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Party extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String description;

    @Column
    private int maxPartyNumber;

    @Column
    private int paymentCycle;

    @Column
    private int pricePerPerson;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private PartyState partyState;

    @Override
    public String toString() {
        return "Party{" +
                "partyId=" + partyId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", maxPartyNumber=" + maxPartyNumber +
                ", paymentCycle=" + paymentCycle +
                ", pricePerPerson=" + pricePerPerson +
                ", serviceType=" + serviceType +
                ", partyState=" + partyState +
                '}';
    }
}
