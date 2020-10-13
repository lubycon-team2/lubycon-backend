package com.rubycon.rubyconteam2.domain.party.domain;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"partyId", "userId"})
})
public class PartyJoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyJoinId;

    @ManyToOne
    @JoinColumn(name = "partyId")
    private Party party;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public static PartyJoin of(User user, Party party, Role role){
        return PartyJoin.builder()
                .user(user)
                .party(party)
                .role(role)
                .build();
    }
}
