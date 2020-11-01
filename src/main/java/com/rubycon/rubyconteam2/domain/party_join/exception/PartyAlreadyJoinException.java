package com.rubycon.rubyconteam2.domain.party_join.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class PartyAlreadyJoinException extends BusinessException {
    public PartyAlreadyJoinException() {
        super(ErrorCode.PARTY_ALREADY_JOIN);
    }
}