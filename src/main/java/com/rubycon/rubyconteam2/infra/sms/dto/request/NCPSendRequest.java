package com.rubycon.rubyconteam2.infra.sms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NCPSendRequest {

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    @ApiModelProperty(value = "핸드폰 번호", required = true, example = "0106500xxxx")
    private String to;
}
