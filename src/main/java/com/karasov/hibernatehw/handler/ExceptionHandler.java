package com.karasov.hibernatehw.handler;

import com.karasov.hibernatehw.handler.exception.CreatingPersonAlreadyExistsException;
import com.karasov.hibernatehw.handler.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CreatingPersonAlreadyExistsException.class)
    public ResponseEntity<String> handleCreatingPersonAlreadyExistsException(CreatingPersonAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
