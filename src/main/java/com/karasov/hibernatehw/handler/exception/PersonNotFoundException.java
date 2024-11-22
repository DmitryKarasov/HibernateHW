package com.karasov.hibernatehw.handler.exception;

public class PersonNotFoundException extends EntityNotFoundException {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
