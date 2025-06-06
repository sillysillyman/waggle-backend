package com.waggle.global.exception.handler;

import com.waggle.global.exception.AccessDeniedException;
import com.waggle.global.exception.JwtTokenException;
import com.waggle.global.exception.ProjectException;
import com.waggle.global.exception.S3Exception;
import com.waggle.global.response.ApiStatus;
import com.waggle.global.response.BaseResponse;
import com.waggle.global.response.ErrorResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<BaseResponse<String>> handleJwtTokenException(JwtTokenException ex) {
        return ErrorResponse.of(ex.getStatus());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<BaseResponse<String>> handleEmptyResultDataAccessException(
        EmptyResultDataAccessException ex) {
        return ErrorResponse.of(ApiStatus._NOT_FOUND);
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleS3Exception(S3Exception ex) {
        return ErrorResponse.of(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<Object>> handleAccessDeniedException(
        AccessDeniedException ex) {
        return ErrorResponse.of(ex.getStatus());
    }

    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<BaseResponse<Object>> handleProjectException(ProjectException ex) {
        return ErrorResponse.of(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Object>> handleIllegalArgumentException(
        IllegalArgumentException ex
    ) {
        return ErrorResponse.of(ApiStatus._BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex
    ) {

        StringBuilder errorMessage = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(fieldName).append(": ").append(message).append("; ");
        });

        return ErrorResponse.of(ApiStatus._BAD_REQUEST, errorMessage.toString());
    }
}
