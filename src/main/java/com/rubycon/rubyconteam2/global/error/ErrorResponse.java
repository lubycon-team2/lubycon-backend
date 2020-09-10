package com.rubycon.rubyconteam2.global.error;

import lombok.*;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String code;
    private String message;
    private int status;
    private List<FieldError> errors;

    @Builder
    public ErrorResponse(String code, String message, int status, List<FieldError> errors) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.errors = errors;
    }

    public static ErrorResponse of(final ErrorCodeType errorCodeType) {
        return ErrorResponse.builder()
                .code(errorCodeType.getCode())
                .message(errorCodeType.getMessage())
                .status(errorCodeType.getStatus())
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse of(final ErrorCodeType errorCodeType, final BindingResult bindingResult){
        final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<FieldError> errors = fieldErrors.stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString())
                        .build())
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(errorCodeType.getCode())
                .message(errorCodeType.getMessage())
                .status(errorCodeType.getStatus())
                .errors(errors)
                .build();
    }

    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        @Builder
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}