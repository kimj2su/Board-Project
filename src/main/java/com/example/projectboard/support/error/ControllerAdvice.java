package com.example.projectboard.support.error;

import com.example.projectboard.support.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> apiExceptionHandler(ApiException e) {
        log.error("ApiException: {}", e.getMessage());
        return ResponseEntity.status(e.getErrorType().getStatus())
                .body(ApiResponse.error(e.getErrorType(), e.getData()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindExceptionHandler(BindException e) {
        Map<String, Object> errorMap = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            log.error("{} BindException - cause :  {}, {}", MDC.get("transactionLog"), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ApiResponse.error(ErrorType.REQUEST_FIELD_ERROR, errorMap);
    }
}
