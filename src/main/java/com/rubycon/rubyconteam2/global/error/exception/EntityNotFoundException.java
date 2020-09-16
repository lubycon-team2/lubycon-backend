package com.rubycon.rubyconteam2.global.error.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
