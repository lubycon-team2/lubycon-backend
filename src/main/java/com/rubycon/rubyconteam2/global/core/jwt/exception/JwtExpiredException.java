package com.rubycon.rubyconteam2.global.core.jwt.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class JwtExpiredException extends BusinessException {
    public JwtExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
