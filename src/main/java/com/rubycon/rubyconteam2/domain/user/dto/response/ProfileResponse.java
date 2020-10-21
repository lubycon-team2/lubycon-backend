package com.rubycon.rubyconteam2.domain.user.dto.response;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponse {

    @ApiModelProperty(value = "사용자 ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "사용자 이름", example = "루비콘")
    private String name;

    @ApiModelProperty(value = "사용자 이미지 URL", example = "https://lh6.googleusercontent.com/-6KJlDdwUinI/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuckKfFRPXo8wZ6oLChNIKzQI2WIZJQ/photo.jpg")
    private String profileUrl;

    @ApiModelProperty(value = "핸드폰 인증 여부", example = "true | false")
    private Boolean isPhoneAuthentication;

    public ProfileResponse(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.profileUrl = user.getProfileUrl();
        this.isPhoneAuthentication = user.getPhoneNumber() == null ? Boolean.FALSE : Boolean.TRUE;
    }
}
