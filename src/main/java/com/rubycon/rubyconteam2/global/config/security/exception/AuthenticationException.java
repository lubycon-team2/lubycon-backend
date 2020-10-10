package com.rubycon.rubyconteam2.global.config.security.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class AuthenticationException extends BusinessException {
    public AuthenticationException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}