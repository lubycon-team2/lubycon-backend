package com.rubycon.rubyconteam2.domain.review.exception;

import com.rubycon.rubyconteam2.global.error.ErrorCode;
import com.rubycon.rubyconteam2.global.error.exception.BusinessException;

public class IsNotReviewableException extends BusinessException {
    public IsNotReviewableException() {
        super(ErrorCode.IS_NOT_REVIEWABLE);
    }
}