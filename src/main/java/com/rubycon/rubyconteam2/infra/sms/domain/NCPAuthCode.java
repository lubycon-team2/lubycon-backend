package com.rubycon.rubyconteam2.infra.sms.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@RedisHash(value = "stock", timeToLive = 300L) // 5 minute
public class NCPAuthCode implements Serializable {

    @Id
    String id; // PhoneNumber
    String authCode;

    /**
     * ID 형태
     * Ex) PhoneNumber:2020-10-20
     */
    public static String generateId(String phoneNumber, LocalDateTime today) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = today.format(dateFormat);
        return phoneNumber + ":" + date;
    }

    public boolean isNotEqualCode(String authCode){
        return !this.authCode.equals(authCode);
    }
}
