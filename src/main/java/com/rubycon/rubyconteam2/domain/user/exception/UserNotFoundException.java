package com.rubycon.rubyconteam2.domain.user.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCodeType;
import com.rubycon.rubyconteam2.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super(ErrorCodeType.USER_NOT_FOUND);
    }
}
