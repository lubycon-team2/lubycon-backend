package com.rubycon.rubyconteam2.infra.sms.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NCPVerifyRequest{

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String to;

    @NotBlank(message = "인증 코드를 입력해주세요.")
    @Pattern(regexp = "[0-9]{6}", message = "6자리 숫자를 입력해주세요")
    private String code;
}
