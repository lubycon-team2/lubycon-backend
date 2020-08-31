package com.rubycon.rubyconteam2.infra.sms;

import lombok.Getter;
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

    @PostMapping("/authenticate/sms/send")
    public String sendSMS(HttpSession httpSession, @RequestBody NCPMessage ncpMessage) throws NoSuchAlgorithmException, InvalidKeyException {
        System.out.println(ncpMessage);
        String result = ncpMessageClient.sendSMS(httpSession, ncpMessage);
        return "Hello";
    }

    @PostMapping("/authenticate/sms/verify")
    public String verify(HttpSession httpSession, @RequestBody NCPAuthCode ncpAuthCode){
        String phoneNumber = ncpAuthCode.getTo();
        String code = (String) httpSession.getAttribute(phoneNumber);
        if(code == null) return "Can't Authenticate phone number !";

        if (!ncpAuthCode.getCode().equals(code)) return "Not Matching AuthCode !";

        // 일치할 경우
        httpSession.removeAttribute(phoneNumber);
        return "Success Auth phone number !";
    }

    @GetMapping("/authenticate/sms/check")
    public String session(HttpSession httpSession){
        String code = (String) httpSession.getAttribute("01065009697");
        return code;
    }
}
