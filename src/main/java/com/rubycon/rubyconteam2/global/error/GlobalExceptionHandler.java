package com.rubycon.rubyconteam2.global.error;

import com.rubycon.rubyconteam2.global.error.exception.BusinessException;
import com.rubycon.rubyconteam2.global.error.exception.NoContentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Set;

/**
 * extends ResponseEntityExceptionHandler
 * 위 핸들러는 스프링에서 기본적으로 제공해주는 Exception을 제공
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @Override
     * RequestBody의 javax.validation 에러
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.debug("handleMethodArgumentNotValid : {}", ex.getMessage());
        final BindingResult bindingResult = ex.getBindingResult();
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, bindingResult);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    /**
     * Path Variables & Request Parameters의 javax.validation 에러
     * @Validated 로 검증하기 때문에 MethodArgumentNotValidException이 아님
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        log.debug("handleConstraintViolationException {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, e);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.debug("handleMethodArgumentTypeMismatchException : {}", e.getMessage());
        return ErrorResponse.of(ErrorCode.METHOD_ARGUMENT_MISMATCHED);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
     * + Security에서 던지는 예외
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.debug("handleAccessDeniedException : {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    /**
     * 리스트의 반환 값이 빈 배열일 때 발생하는 No Content 핸들러
     */
    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected void handleNoContentException(){
    }

    /**
     * 비즈니스 로직 Exception
     * 개발자가 직접 생성한 Exception
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLoginException(BusinessException e) {
        log.debug("handleBusinessLoginException : {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    /**
     * 처리할 수 없는 Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleIntervalException(Exception e) {
        log.error("handleIntervalException : ", e);
        return ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e);
    }

}
