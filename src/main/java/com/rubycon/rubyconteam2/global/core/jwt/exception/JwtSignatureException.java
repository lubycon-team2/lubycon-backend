package com.rubycon.rubyconteam2.global.core.jwt.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class JwtSignatureException extends BusinessException {
    public JwtSignatureException() {
        super(ErrorCode.TOKEN_SIGNATURE_INVALID);
    }
}
