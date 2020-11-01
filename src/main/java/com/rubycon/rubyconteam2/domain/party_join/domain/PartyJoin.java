package com.rubycon.rubyconteam2.domain.party_join.domain;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    @Setter
    private LocalDateTime joinDate;

    @Column
    @Setter
    private LocalDateTime leaveDate;

    @Column
    @Setter
    private Boolean isDeleted;

    public static PartyJoin of(User user, Party party, Role role){
        return PartyJoin.builder()
                .user(user)
                .party(party)
                .role(role)
                .joinDate(LocalDateTime.now())
                .leaveDate(null)
                .isDeleted(Boolean.FALSE)
                .build();
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public boolean isPresent() { return this.isDeleted ? Boolean.FALSE : Boolean.TRUE; }

    public boolean isEquals(Long userId) {
        return this.user.getUserId().equals(userId);
    }

    public boolean isNotEquals(Long userId) {
        return !this.user.getUserId().equals(userId);
    }

    /**
     * 리뷰 가능한 조건에 부합하는지 검사
     * 자신의 참가 시점 > 특정 사용자 탈퇴 시점 : 제외
     */
    public boolean isReviewable(PartyJoin myPartyJoin) {

        if (this.isPresent()) return true;
        if (this.leaveDate == null) return false;

        LocalDateTime myJoinDate = myPartyJoin.getJoinDate();
        return myJoinDate.isBefore(this.leaveDate);
    }
}
