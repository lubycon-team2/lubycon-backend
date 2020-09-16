package com.rubycon.rubyconteam2.global.core.jwt;

import com.rubycon.rubyconteam2.global.config.security.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.token-secret}") public String JWT_SECRET;

    @Override
    public String createToken(String oauthKey, String name) {
        return Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes())
                        .setHeader(createHeader())
                        .setClaims(createPayloads(oauthKey, name))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRE_TIME))
                        .compact();
    }

    @Override
    public Claims getPayloadsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰의 검증 + 만료일자 체크
    @Override
    public boolean verifyToken(String token) {
        try {
            Claims claims = getPayloadsFromToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 헤더 생성
    private Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();

        header.put("typ", SecurityConstants.TOKEN_TYPE);
        header.put("alg", SecurityConstants.TOKEN_ALGORITHM);
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    // 토큰 페이로드 생성
    private Map<String, Object> createPayloads(String oauthKey, String name){
        Map<String, Object> payloads = new HashMap<>();

        payloads.put("id", oauthKey);
        payloads.put("name", name);

        return payloads;
    }
}
