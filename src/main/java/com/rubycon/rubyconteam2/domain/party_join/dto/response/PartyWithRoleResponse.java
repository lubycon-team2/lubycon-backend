package com.rubycon.rubyconteam2.domain.party_join.dto.response;

import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party_join.domain.Role;
import com.rubycon.rubyconteam2.domain.party.dto.response.PartyResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyWithRoleResponse {

    private PartyResponse party;

    @ApiModelProperty(value = "파티 권한", example = "파티장 | 파티원")
    private Role role;

    public PartyWithRoleResponse(PartyJoin partyJoin) {
        this.party = new PartyResponse(partyJoin.getParty());
        this.role = partyJoin.getRole();
    }
}
