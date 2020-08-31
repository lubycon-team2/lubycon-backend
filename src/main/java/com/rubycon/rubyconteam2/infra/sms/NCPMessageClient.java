package com.rubycon.rubyconteam2.infra.sms;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class NCPMessageClient {

    @Value("${app.sms.ncp.access-key}")
    private String NCP_ACCESS_KEY;

    @Value("${app.sms.ncp.secret-key}")
    private String NCP_SECRET_KEY;

    @Value("${app.sms.ncp.service-id}")
    private String NCP_SERVICE_ID;

    private final String BASE_URL = "https://sens.apigw.ntruss.com";
    private final String HEADER_TIMESTAMP = "x-ncp-apigw-timestamp";
    private final String HEADER_ACCESS_KEY = "x-ncp-iam-access-key";
    private final String HEADER_SIGNATURE = "x-ncp-apigw-signature-v2";
    private String SEND_REQUEST_URL;

    private final RestTemplate restTemplate;

    public NCPMessageClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void init() {
        this.SEND_REQUEST_URL = "/sms/v2/services/" + NCP_SERVICE_ID + "/messages";
    }

    // NCP SENS 서비스를 이용한 SMS 보내기
    public void sendSMS(HttpSession httpSession, NCPMessage ncpMessage) throws InvalidKeyException, NoSuchAlgorithmException {
        String url = BASE_URL + SEND_REQUEST_URL;
        String message = "[Rubycon Party-ing]\n인증번호 : ";
        String authCode = generateAuthCode();

        // TODO : 더 좋은 방법?
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> user = new HashMap<>();
        user.put("to", ncpMessage.getTo());
        list.add(user);

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // set custom header
        headers.set(HEADER_TIMESTAMP, getTimestamp());
        headers.set(HEADER_ACCESS_KEY, NCP_ACCESS_KEY);
        headers.set(HEADER_SIGNATURE, getSignature());

        // create a map for post parameters
        Map<String, Object> map = new HashMap<>();
        map.put("type", "SMS");
        map.put("contentType", "COMM");
        map.put("countryCode", "82");
        map.put("from", "01065009697");
        map.put("content", message + authCode);
        map.put("messages", list);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        try {
            ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);
            httpSession.setAttribute(ncpMessage.getTo(), authCode);
            System.out.println(response);
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
        }
    }

    // 현재 시각
    private String getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.valueOf(timestamp.getTime());
    }

    // NCP SENS에서 설계한 Signature 설정
    private String getSignature() throws NoSuchAlgorithmException, InvalidKeyException {

        String space = " ";                                    // one space
        String newLine = "\n";                                 // new line
        String method = "POST";                                // method

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(SEND_REQUEST_URL)
                .append(newLine)
                .append(getTimestamp())
                .append(newLine)
                .append(NCP_ACCESS_KEY)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(NCP_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(rawHmac);
    }

    // 6자리 랜덤 인증 코드 메시지
    private String generateAuthCode(){
        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());
        int number = generator.nextInt(1000000) % 1000000;
        return String.valueOf(number);
    }
}
