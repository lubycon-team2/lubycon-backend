package com.rubycon.rubyconteam2.infra.sms.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class SMSCodeExpiredException extends BusinessException {
    public SMSCodeExpiredException() {
        super(ErrorCode.SMS_CODE_EXPIRED);
    }
}
