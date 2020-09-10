package com.rubycon.rubyconteam2.global.error;

import lombok.Getter;

@Getter
public enum ErrorCodeType {

    // Common
    INVALID_INPUT_VALUE(400, "COMMON_001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "COMMON_002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "COMMON_003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "COMMON_004", "Interval Server Error"),
    INVALID_TYPE_VALUE(400, "COMMON_005", "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "COMMON_006", "Access is Denied"),
    INVALID_TOKEN(400, "COMMON_008", "토큰이 올바르지 않습니다."),
    METHOD_ARGUMENT_MISMATCHED(400, "COMMON_009", "Request Argument 타입이 일치하지 않습니다."),

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

    ErrorCodeType(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
