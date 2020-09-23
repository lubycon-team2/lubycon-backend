package com.rubycon.rubyconteam2.infra.sms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NCPResponse {
    @ApiModelProperty(value = "일반 메시지", required = true, example = "Example - Success")
    private String message;
}