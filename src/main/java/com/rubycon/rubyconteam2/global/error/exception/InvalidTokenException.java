package com.rubycon.rubyconteam2.global.error.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCodeType;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException() {
        super(ErrorCodeType.INVALID_TOKEN);
    }
}
