package com.campuslab.platform.u202417448.shared.interfaces.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messages;

    public GlobalExceptionHandler(MessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception, Locale locale) {
        var errors = exception.getBindingResult().getAllErrors();
        String message = errors.isEmpty() ? message("request.invalid", locale)
                : messages.getMessage(errors.get(0), locale);
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableRequest(Locale locale) {
        return ResponseEntity.badRequest().body(new ErrorResponse(message("request.invalid", locale)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgument(IllegalArgumentException exception, Locale locale) {
        String fallback = message("request.invalid", locale);
        String detail = exception.getMessage() == null ? fallback
                : messages.getMessage(exception.getMessage(), null, fallback, locale);
        return ResponseEntity.badRequest().body(new ErrorResponse(detail));
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<ErrorResponse> handleHttpError(ErrorResponseException exception, Locale locale) {
        var status = exception.getStatusCode();
        String key = status.is5xxServerError() ? "request.unexpected" : "request.invalid";
        return ResponseEntity.status(status).body(new ErrorResponse(message(key, locale)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception, Locale locale) {
        LOGGER.error("Unexpected API error.", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(message("request.unexpected", locale)));
    }

    private String message(String key, Locale locale) {
        return messages.getMessage(key, null, locale);
    }
}
