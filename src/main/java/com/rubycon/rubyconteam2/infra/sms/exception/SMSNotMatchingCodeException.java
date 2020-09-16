package com.rubycon.rubyconteam2.infra.sms.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class SMSNotMatchingCodeException extends BusinessException {
    public SMSNotMatchingCodeException() {
        super(ErrorCode.SMS_NOT_MATCHING_CODE);
    }
}
