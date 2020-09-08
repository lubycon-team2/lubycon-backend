package com.rubycon.rubyconteam2.global.error.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCodeType;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCodeType errorCodeType) {
        super(errorCodeType);
    }
}
