package com.rubycon.rubyconteam2.global.core.jwt;

import com.rubycon.rubyconteam2.global.config.security.SecurityConstants;
import io.jsonwebtoken.*;
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

        header.put("typ", SecurityConstants.TOKEN_TYPE);
        header.put("alg", SecurityConstants.TOKEN_ALGORITHM);
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private Map<String, Object> createPayloads(String oauthKey, String name){
        Map<String, Object> payloads = new HashMap<>();

        payloads.put("id", oauthKey);
        payloads.put("name", name);

        return payloads;
    }

    private boolean isTokenExpired(String token){
        return getPayloadsFromToken(token).getExpiration().before(new Date());
    }
}
