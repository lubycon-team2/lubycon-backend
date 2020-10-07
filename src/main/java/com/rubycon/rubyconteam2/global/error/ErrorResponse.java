package com.rubycon.rubyconteam2.global.error;

import lombok.*;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String code;
    private String message;
    private int status;
    private List<FieldError> errors;

    public static ErrorResponse of(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .errors(new ArrayList<>())
                .build();
    }

    public static ErrorResponse of(final ErrorCode errorCode, final List<FieldError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .errors(errors)
                .build();
    }

    // MethodArgumentNotValid Exception 처리 메서드
    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult){
        final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<FieldError> errors = fieldErrors.stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString())
                        .build())
                .collect(Collectors.toList());

        return ErrorResponse.of(errorCode, errors);
    }

    // ConstraintViolationException Exception 처리 메서드
    public static ErrorResponse of(final ErrorCode errorCode, final ConstraintViolationException e){
        Set<ConstraintViolation<?>> descriptors = e.getConstraintViolations();
        List<FieldError> errors = descriptors.stream()
                .map(violation -> {
                    String reason = violation.getMessage();
                    String value = violation.getInvalidValue().toString();
                    String field = null;
                    for(Path.Node node : violation.getPropertyPath()) field = node.getName();

                    return ErrorResponse.FieldError.builder()
                            .reason(reason)
                            .field(field)
                            .value(value)
                            .build();
                })
                .collect(Collectors.toList());
        return ErrorResponse.of(errorCode, errors);
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