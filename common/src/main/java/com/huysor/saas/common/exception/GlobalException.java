package com.huysor.saas.common.exception;

import com.huysor.saas.common.dto.res.ApiRes;
import feign.FeignException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiRes<String>> handleNotFound(NotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiRes.error("Resource not found", null, HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler()
    public ResponseEntity<ApiRes<String>> handleNotFound(NoResourceFoundException ex) {
        log.error("not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiRes.error("Resource not found", null, HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiRes<String>> handleBadRequest(BadRequestException ex) {
        log.error("Bad request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiRes.error(ex.getMessage()));
    }

    @ExceptionHandler(HttpResponseException.class)
    public ResponseEntity<ApiRes<String>> handleHttpResponseException(HttpResponseException ex) {
        log.error("HTTP response error: status={}, message={}", ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatusCode()))
                .body(ApiRes.error("HTTP response error: " + ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRes<String>> handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiRes.error("An unexpected error occurred", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiRes<String>> handleUnreadableBody(HttpMessageNotReadableException ex) {
        log.error("Can't read json body {}", ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiRes.error("Invalid Request Input", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(FeignException.Unauthorized.class)
    public ResponseEntity<ApiRes<String>> handleUnAuthorization(FeignException ex) {
        log.error("don't have permission to access this resource: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiRes.error("Your don't have permission to access this resource !!!", null, HttpStatus.FORBIDDEN.value()));

    }


}
