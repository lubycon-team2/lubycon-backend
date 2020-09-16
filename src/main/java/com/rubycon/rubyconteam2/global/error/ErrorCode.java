package com.rubycon.rubyconteam2.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "COMMON_001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "COMMON_002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "COMMON_003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "COMMON_004", "Interval Server Error"),
    INVALID_TYPE_VALUE(400, "COMMON_005", "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "COMMON_006", "Access is Denied"),
    METHOD_ARGUMENT_MISMATCHED(400, "COMMON_007", "Request Argument 타입이 일치하지 않습니다."),
    UNAUTHORIZED(401, "COMMON_008", "인증되지 않은 사용자입니다."),

    // JWT
    TOKEN_SIGNATURE_INVALID(401, "J001", "유효하지 않은 토큰입니다."),
    TOKEN_MALFORMED(401, "J002", "손상된 토큰입니다."),
    TOKEN_EXPIRED(401, "J003", "만료된 토큰입니다."),
    TOKEN_UNSUPPORTED(401, "J004", "예상되는 형식과 일치하지 않는 토큰입니다."),
    TOKEN_ILLEGAL_ARGUMENT(401, "J005", "토큰이 'null' 또는 빈 문자열입니다."),

    // SMS
    SMS_CODE_EXPIRED(401, "S001", "만료된 인증 코드입니다."),
    SMS_NOT_MATCHING_CODE(401, "S002", "인증 코드가 일치하지 않습니다."),

    // USER
    USER_NOT_FOUND(400, "U001", "사용자를 찾을 수 없습니다."),

    // EX) Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    // EX) Coupon
    COUPON_ALREADY_USE(400, "CO001", "Coupon was already used"),
    COUPON_EXPIRE(400, "CO002", "Coupon was already expired")
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
