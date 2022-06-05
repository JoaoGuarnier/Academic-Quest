package com.academicquest.controller.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.academicquest.service.exception.DatabaseException;
import com.academicquest.service.exception.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler{


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Resource not found");
        standardError.setMessage("Entity not found");
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.BAD_REQUEST.value());
        standardError.setError("Database exception");
        standardError.setMessage(exception.getMessage());
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        validationError.setError("Validation exception");
        validationError.setMessage(exception.getMessage());
        validationError.setPath(request.getRequestURI());

        exception.getBindingResult().getFieldErrors()
                .stream()
                .forEach(fieldError -> validationError.addError(fieldError.getField(), fieldError.getDefaultMessage()));


        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
    }

}
