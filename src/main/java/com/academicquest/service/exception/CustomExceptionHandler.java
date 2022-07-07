package com.academicquest.service.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<MessageException> handleBadRequestException(BadRequestException e, HttpServletRequest req) {

        MessageException me = MessageException.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .uri(req.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(me);
    }

    @ExceptionHandler(value = {DataIntegrityException.class})
    public ResponseEntity<MessageException> handleDataIntegrityException(DataIntegrityException e, HttpServletRequest req) {

        MessageException me = MessageException.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.CONFLICT.value())
                .timeStamp(LocalDateTime.now())
                .uri(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(me);
    }
}