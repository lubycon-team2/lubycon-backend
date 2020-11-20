package com.rubycon.rubyconteam2.infra.sms.service;

import com.rubycon.rubyconteam2.domain.user.service.UserService;
import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;
import com.rubycon.rubyconteam2.infra.sms.domain.NCPAuthCode;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPSendRequest;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPVerifyRequest;
import com.rubycon.rubyconteam2.infra.sms.exception.SMSCodeExpiredException;
import com.rubycon.rubyconteam2.infra.sms.exception.SMSNotMatchingCodeException;
import com.rubycon.rubyconteam2.infra.sms.repository.NCPAuthCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class NCPMessageService {

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
    private final String AUTH_MESSAGE = "[Rubycon Party-ing]\n인증번호 : ";

    private String SEND_REQUEST_URL;
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final NCPAuthCodeRepository ncpAuthCodeRepository;

    public NCPMessageService(RestTemplateBuilder restTemplateBuilder, UserService userService, NCPAuthCodeRepository ncpAuthCodeRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.userService = userService;
        this.ncpAuthCodeRepository = ncpAuthCodeRepository;
    }

    @PostConstruct
    public void init() {
        this.SEND_REQUEST_URL = "/sms/v2/services/" + NCP_SERVICE_ID + "/messages";
    }

    // NCP SENS 서비스를 이용한 SMS 보내기
    public void sendSMS(NCPSendRequest ncpSendRequest) throws InvalidKeyException, NoSuchAlgorithmException {
        final String url = BASE_URL + SEND_REQUEST_URL;
        final String authCode = generateAuthCode();

        // Create headers
        HttpHeaders headers = this.createHeaders();

        // Create a map for post parameters
        Map<String, Object> map = this.createPostParameters(authCode, ncpSendRequest);

        // Build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // Send POST request
        try {
            ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);
            log.info(String.valueOf(response));

            NCPAuthCode ncpAuthCode = ncpSendRequest.toEntity(authCode);
            ncpAuthCodeRepository.save(ncpAuthCode);
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 인증 코드 검증 로직
     * @param ncpVerifyRequest
     * @return 검증 성공 여부
     */
    public void verifyAuthenticationCode (Long userId, NCPVerifyRequest ncpVerifyRequest){
        String phoneNumber = ncpVerifyRequest.getTo();
        String code = ncpVerifyRequest.getCode();

        String id = NCPAuthCode.generateId(phoneNumber, LocalDateTime.now());
        NCPAuthCode ncpAuthCode = ncpAuthCodeRepository.findById(id)
                .orElseThrow(SMSCodeExpiredException::new);
        System.out.println(ncpAuthCode);

        if (ncpAuthCode.isNotEqualCode(code)) throw new SMSNotMatchingCodeException();

        // 일치할 경우
        ncpAuthCodeRepository.delete(ncpAuthCode);
        userService.update(userId, ncpVerifyRequest);
    }

    /**
     * Header 생성
     */
    private HttpHeaders createHeaders() throws InvalidKeyException, NoSuchAlgorithmException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set custom header
        headers.set(HEADER_TIMESTAMP, getTimestamp());
        headers.set(HEADER_ACCESS_KEY, NCP_ACCESS_KEY);
        headers.set(HEADER_SIGNATURE, getSignature());
        return headers;
    }

    /**
     * NCP 에 전달할 POST parameter
     */
    private Map<String, Object> createPostParameters(String authCode, NCPSendRequest ncpSendRequest){
        Map<String, Object> map = new HashMap<>();
        map.put("type", "SMS");
        map.put("contentType", "COMM");
        map.put("countryCode", "82");
        map.put("from", "01065009697");
        map.put("content", AUTH_MESSAGE + authCode);
        map.put("messages", ncpSendRequest.createMessages());
        return map;
    }

    /**
     * 현재 시각 반환 메서드
     * @return 현재 시각
     */
    private String getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.valueOf(timestamp.getTime());
    }

    /**
     * NCP SENS에서 설계한 Signature 설정 반환 메서드
     * @return NCP SENS Sigranture 반환
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
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

    /**
     * 6자리 랜덤 인증 코드 생성 메서드
     * @return 6자리 인증 코드
     */
    private String generateAuthCode(){
        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());
        int number = generator.nextInt(900000) + 100000;
        return String.valueOf(number);
    }
}
