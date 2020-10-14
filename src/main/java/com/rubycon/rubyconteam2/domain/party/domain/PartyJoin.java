package com.rubycon.rubyconteam2.domain.party.domain;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import lombok.*;

import javax.crypto.BadPaddingException;
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

    @Column
    @Setter
    private Boolean isDeleted;

    public static PartyJoin of(User user, Party party, Role role){
        return PartyJoin.builder()
                .user(user)
                .party(party)
                .role(role)
                .isDeleted(Boolean.FALSE)
                .build();
    }

    public Boolean isDeleted() {
        return this.isDeleted;
    }

    public Boolean isPresent() { return this.isDeleted ? Boolean.FALSE : Boolean.TRUE; }
}
