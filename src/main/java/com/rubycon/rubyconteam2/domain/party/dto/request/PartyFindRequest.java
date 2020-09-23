package com.rubycon.rubyconteam2.domain.party.dto.request;

import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.global.common.model.ValueOfEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PartyFindRequest {

    @NotEmpty(message = "서비스 타입을 입력해주세요 \nNETFLIX | WATCHA | WAAVE | APPLE_MUSIC")
    @ValueOfEnum(enumClass = ServiceType.class)
    @ApiModelProperty(value = "서비스 타입", required = true, example = "NETFLIX | WATCHA | WAAVE | APPLE_MUSIC")
    private String serviceType;

    public ServiceType getServiceType(){
        return ServiceType.valueOf(serviceType);
    }
}
