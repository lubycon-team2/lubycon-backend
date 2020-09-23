package com.rubycon.rubyconteam2.global.core.jwt.controller;

import com.rubycon.rubyconteam2.domain.user.domain.Role;
import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.domain.user.service.UserService;
import com.rubycon.rubyconteam2.global.core.jwt.service.JwtService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jwt")
@RequiredArgsConstructor
@Slf4j
public class JwtTestController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping("/verify")
    @ApiImplicitParam(name = "Authorization", value = "Not Required")
    public Claims isValidToken(@RequestHeader("token") String token) {
        log.debug("isValidate {}", jwtService.verifyToken(token));
        return jwtService.getPayloadsFromToken(token);
    }

    @PostMapping("/create")
    @ApiImplicitParam(name = "Authorization", value = "Not Required")
    public String createJwtToken(
            @RequestParam("name") String name,
            @RequestParam("oauthKey") String key){

        userRepository.findByOauthKey(key).orElseGet(() -> {
            User user = User.builder()
                    .email("test@naver.com")
                    .name(name)
                    .oauthKey(key)
                    .providerType("google")
                    .role(Role.GUEST)
                    .build();
            return userRepository.save(user);
        });
        return jwtService.createToken(key, name);
    }
}
