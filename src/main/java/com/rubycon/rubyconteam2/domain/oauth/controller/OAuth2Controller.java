package com.rubycon.rubyconteam2.domain.oauth.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @GetMapping("/me")
    @ApiOperation(value = "테스트 - 현재 로그인 된 사용자 정보 확인 API", notes = "로그인이 필요한 API\n 토큰을 담아 보내면 사용자 정보를 얻을 수 있습니다.")
    public OAuth2User me(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User;
    }
}
