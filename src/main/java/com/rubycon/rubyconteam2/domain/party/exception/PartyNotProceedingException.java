package com.rubycon.rubyconteam2.domain.party.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class PartyNotProceedingException extends BusinessException {
    public PartyNotProceedingException() {
        super(ErrorCode.PARTY_NOT_PROCEEDING);
    }
}