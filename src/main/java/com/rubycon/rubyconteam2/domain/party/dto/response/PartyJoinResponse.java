package com.rubycon.rubyconteam2.domain.party.dto.response;

import com.rubycon.rubyconteam2.domain.party.domain.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyJoinResponse {


    @ApiModelProperty(value = "파티 참여 ID", example = "1")
    private Long partyJoinId;

    @ApiModelProperty(value = "사용자 ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "파티 ID", example = "1")
    private Long partyId;

    @ApiModelProperty(value = "파티 권한 (MEMBER)", example = "MEMBER")
    private Role role;

    public PartyJoinResponse(PartyJoin partyJoin) {
        this.partyJoinId = partyJoin.getPartyJoinId();
        this.userId = partyJoin.getUser().getUserId();
        this.partyId = partyJoin.getParty().getPartyId();
        this.role = partyJoin.getRole();
    }
}