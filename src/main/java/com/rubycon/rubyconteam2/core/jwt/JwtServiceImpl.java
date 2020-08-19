package com.rubycon.rubyconteam2.core.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 8; // 8시간

    @Override
    public String createToken(String oauthKey, String name) {
        return Jwts.builder()
                        .setHeader(createHeader())
                        .setClaims(createPayloads(oauthKey, name))
                        .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes())
                        .compact();
    }

    @Override
    public Claims getPayloadsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    // TODO: Error 처리 수정해야함
    @Override
    public boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(JWT_SECRET.getBytes())
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
        } catch (IllegalArgumentException e) {
            log.error("Empty JWT claims");
        }
        return false;
    }

    private Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private Map<String, Object> createPayloads(String oauthKey, String name){
        Map<String, Object> payloads = new HashMap<>();

        payloads.put("id", oauthKey);
        payloads.put("name", name);

        return payloads;
    }
}
