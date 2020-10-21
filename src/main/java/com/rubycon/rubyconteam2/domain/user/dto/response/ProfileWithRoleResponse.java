package com.rubycon.rubyconteam2.domain.user.dto.response;

import com.rubycon.rubyconteam2.domain.party.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party.domain.Role;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileWithRoleResponse {

    @ApiModelProperty(value = "사용자 ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "사용자 이름", example = "루비콘")
    private String name;

    @ApiModelProperty(value = "파티 권한", example = "파티장 | 파티원")
    private Role role;

    public ProfileWithRoleResponse(PartyJoin partyJoin) {
        User user = partyJoin.getUser();

        this.userId = user.getUserId();
        this.name = user.getName();
        this.role = partyJoin.getRole();
    }
}
