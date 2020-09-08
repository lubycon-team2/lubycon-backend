package com.rubycon.rubyconteam2.infra.sms;

import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@Slf4j
public class NCPMessageController {

    @Autowired
    NCPMessageClient ncpMessageClient;

    // TODO : UserService로 변경
    @Autowired
    UserRepository userRepository;

    @PostMapping("/authenticate/sms/send")
    public String sendSMS(HttpSession httpSession, @RequestBody NCPSendRequest ncpSendRequest) throws NoSuchAlgorithmException, InvalidKeyException {
        System.out.println(ncpSendRequest);
        ncpMessageClient.sendSMS(httpSession, ncpSendRequest);
        return "Success send SMS";
    }

    @PostMapping("/authenticate/sms/verify")
    public String verify(HttpSession httpSession, @RequestBody NCPVerifyRequest ncpVerifyRequest){
        String phoneNumber = ncpVerifyRequest.getTo();
        String code = (String) httpSession.getAttribute(phoneNumber);
        if(code == null) return "Can't Authenticate phone number !";

        if (!ncpVerifyRequest.getCode().equals(code)) return "Not Matching AuthCode !";

        // 일치할 경우
        httpSession.removeAttribute(phoneNumber);
        // TODO : DB 저장 로직 구현하기
        return "Success Auth phone number !";
    }
}
