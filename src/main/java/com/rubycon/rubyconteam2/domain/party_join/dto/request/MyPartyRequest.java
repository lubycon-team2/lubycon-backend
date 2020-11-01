package com.rubycon.rubyconteam2.domain.party_join.dto.request;

import com.rubycon.rubyconteam2.domain.party.domain.PartyState;
import com.rubycon.rubyconteam2.global.common.anotation.ValueOfEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyPartyRequest {

    @NotEmpty(message = "파티 상태를 입력해주세요 \nRECRUITING | COMPLETED | DELETED")
    @ValueOfEnum(enumClass = PartyState.class)
    @ApiModelProperty(value = "파티 상태", required = true, example = "RECRUITING | COMPLETED | DELETED")
    private String partyState;

    public PartyState getPartyState(){
        return PartyState.valueOf(partyState);
    }
}
