package com.rubycon.rubyconteam2.infra.sms.controller;

import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.domain.user.service.UserService;
import com.rubycon.rubyconteam2.infra.sms.dto.response.NCPResponse;
import com.rubycon.rubyconteam2.infra.sms.service.NCPMessageService;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPSendRequest;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPVerifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public NCPResponse sendSMS(
            HttpSession httpSession,
            @RequestBody @Valid NCPSendRequest ncpSendRequest
    ) throws NoSuchAlgorithmException, InvalidKeyException {
        ncpMessageService.sendSMS(httpSession, ncpSendRequest);
        return new NCPResponse("Success send SMS");
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public NCPResponse verify(
            HttpSession httpSession,
            @RequestBody @Valid NCPVerifyRequest ncpVerifyRequest
    ){
        ncpMessageService.verifyAuthenticationCode(httpSession, ncpVerifyRequest);
        return new NCPResponse("Success verify phone number");
    }
}
