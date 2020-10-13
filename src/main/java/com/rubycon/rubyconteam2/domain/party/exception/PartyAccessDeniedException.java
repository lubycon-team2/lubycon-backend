package com.rubycon.rubyconteam2.domain.party.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class PartyAccessDeniedException extends BusinessException {
    public PartyAccessDeniedException() {
        super(ErrorCode.PARTY_ACCESS_DENIED);
    }
}