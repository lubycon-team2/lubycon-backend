package com.rubycon.rubyconteam2.infra.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@Slf4j
public class NCPMessageController {

    @Autowired
    NCPMessageClient ncpMessageClient;

    @GetMapping("/sendSMS")
    public String sendSMS() throws NoSuchAlgorithmException, InvalidKeyException {
        String result = ncpMessageClient.sendSMS("01065009697");
        log.debug(result);
        return "Hello";
    }
}
