package com.ex.flightbooking.config;

public class BusinessLogicException extends RuntimeException {
    public BunssinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}