package com.rubycon.rubyconteam2.infra.sms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NCPSendRequest {

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    @ApiModelProperty(value = "핸드폰 번호", required = true, example = "0106500xxxx")
    private String to;

    public List<Map<String, String>> createMessages(){
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> user = new HashMap<>();
        user.put("to", this.to);
        list.add(user);

        return list;
    }
}
