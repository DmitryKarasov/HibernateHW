package com.karasov.hibernatehw.handler.exception;

public class CreatingPersonAlreadyExistsException extends RuntimeException {
    public CreatingPersonAlreadyExistsException(String message) {
        super(message);
    }
}
