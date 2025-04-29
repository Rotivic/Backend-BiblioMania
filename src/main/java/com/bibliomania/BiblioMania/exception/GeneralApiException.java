package com.bibliomania.BiblioMania.exception;

public class GeneralApiException extends RuntimeException {
    private final int errorCode;

    public GeneralApiException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
