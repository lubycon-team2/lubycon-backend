package com.rubycon.rubyconteam2.global.core.jwt.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class JwtUnsupportedException extends BusinessException {
    public JwtUnsupportedException() {
        super(ErrorCode.TOKEN_UNSUPPORTED);
    }
}
