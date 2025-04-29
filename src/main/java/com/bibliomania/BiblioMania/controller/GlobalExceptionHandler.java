package com.bibliomania.BiblioMania.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.bibliomania.BiblioMania.exception.BookNotFoundException;
import com.bibliomania.BiblioMania.exception.GeneralApiException;
import com.bibliomania.BiblioMania.model.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
 
    @ExceptionHandler(GeneralApiException.class)
    public ResponseEntity<ApiError> handleGeneralApiException(GeneralApiException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            ex.getErrorCode(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.valueOf(ex.getErrorCode())).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Ha ocurrido un error inesperado.",
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
