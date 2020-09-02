package com.rubycon.rubyconteam2.global.error;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String code;
    private String message;

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(ErrorCodeType errorCodeType) {
        return ErrorResponse.builder()
                .code(errorCodeType.getCode())
                .message(errorCodeType.getMessage())
                .build();
    }
}