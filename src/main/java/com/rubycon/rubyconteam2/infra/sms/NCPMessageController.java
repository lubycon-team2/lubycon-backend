package com.rubycon.rubyconteam2.infra.sms;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.domain.user.service.UserService;
import com.rubycon.rubyconteam2.infra.sms.dto.NCPSendRequest;
import com.rubycon.rubyconteam2.infra.sms.dto.NCPVerifyRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    final NCPMessageClient ncpMessageClient;

    final UserService userService;

    final UserRepository userRepository;

    @PostMapping("/send")
    public String sendSMS(
            HttpSession httpSession,
            @RequestBody @Valid NCPSendRequest ncpSendRequest
    ) throws NoSuchAlgorithmException, InvalidKeyException {
        System.out.println(ncpSendRequest);
        ncpMessageClient.sendSMS(httpSession, ncpSendRequest);
        return "Success send SMS";
    }

    @PostMapping("/verify")
    public String verify(
            HttpSession httpSession,
            @RequestBody @Valid NCPVerifyRequest ncpVerifyRequest
    ){
        return ncpMessageClient.verifyAuthenticationCode(httpSession, ncpVerifyRequest);
    }
}
