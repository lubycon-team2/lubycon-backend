package com.rubycon.rubyconteam2.global.core.jwt;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String createToken(String oauthKey, String name);

    Claims getPayloadsFromToken(String token);

    boolean verifyToken(String token);
}
