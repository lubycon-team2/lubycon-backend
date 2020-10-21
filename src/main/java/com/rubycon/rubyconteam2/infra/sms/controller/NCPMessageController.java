package com.rubycon.rubyconteam2.infra.sms.controller;

import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.domain.user.service.UserService;
import com.rubycon.rubyconteam2.global.config.oauth.constants.OAuthConstants;
import com.rubycon.rubyconteam2.global.config.security.exception.AuthenticationException;
import com.rubycon.rubyconteam2.infra.sms.dto.response.NCPResponse;
import com.rubycon.rubyconteam2.infra.sms.service.NCPMessageService;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPSendRequest;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPVerifyRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/authenticate/sms")
@RequiredArgsConstructor
@Slf4j
public class NCPMessageController {

    final NCPMessageService ncpMessageService;

    final UserService userService;

    final UserRepository userRepository;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "NCP SENS 휴대폰 인증 코드 보내기 API", notes = "요청을 보내면 6자리 랜덤 인증 코드가 특정 핸드폰 번호로 보내집니다.")
    public NCPResponse sendSMS(
            @ApiIgnore HttpSession httpSession,
            @RequestBody @Valid NCPSendRequest ncpSendRequest
    ) throws NoSuchAlgorithmException, InvalidKeyException {
        ncpMessageService.sendSMS(httpSession, ncpSendRequest);
        return new NCPResponse("Success send SMS");
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "NCP SENS 휴대폰 인증 코드 검증 API", notes = "6자리 랜덤 인증 코드와 핸드폰 번호를 넘겨주면 서버에서 인증 코드를 검증해줍니다.")
    public NCPResponse verify(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @ApiIgnore HttpSession httpSession,
            @RequestBody @Valid NCPVerifyRequest ncpVerifyRequest
    ){
        if (oAuth2User == null) throw new AuthenticationException();

        Long userId = oAuth2User.getAttribute(OAuthConstants.KEY);
        ncpMessageService.verifyAuthenticationCode(httpSession, userId, ncpVerifyRequest);
        return new NCPResponse("Success verify phone number");
    }
}
