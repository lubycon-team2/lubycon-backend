package com.rubycon.rubyconteam2.core.jwt;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jwt/verify")
@Slf4j
public class JwtTestController {

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public Claims isValidToken(@RequestHeader("token") String token) {
        log.debug("isValidate {}", jwtService.verifyToken(token));
        return jwtService.getPayloadsFromToken(token);
    }

    @PostMapping
    public String createJwtToken(
            @RequestParam("name") String name,
            @RequestParam("oauthKey") String key){
        return jwtService.createToken(key, name);
    }
}