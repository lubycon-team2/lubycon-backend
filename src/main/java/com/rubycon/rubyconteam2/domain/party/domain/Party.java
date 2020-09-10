package com.rubycon.rubyconteam2.domain.party.domain;

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
}