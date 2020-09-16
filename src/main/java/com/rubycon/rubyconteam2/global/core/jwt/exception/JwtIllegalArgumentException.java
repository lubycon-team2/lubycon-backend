package com.rubycon.rubyconteam2.global.core.jwt.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class JwtIllegalArgumentException extends BusinessException {
    public JwtIllegalArgumentException() {
        super(ErrorCode.TOKEN_ILLEGAL_ARGUMENT);
    }
}
