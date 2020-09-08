package com.rubycon.rubyconteam2.global.error.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCodeType;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCodeType errorCodeType;

    public BusinessException() {
    }

    public BusinessException(String message, ErrorCodeType errorCodeType) {
        super(message);
        this.errorCodeType = errorCodeType;
    }

    public BusinessException(ErrorCodeType errorCodeType) {
        super(errorCodeType.getMessage());
        this.errorCodeType = errorCodeType;
    }
}
