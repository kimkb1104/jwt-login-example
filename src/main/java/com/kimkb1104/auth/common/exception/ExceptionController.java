package com.kimkb1104.auth.common.exception;

import com.kimkb1104.auth.common.converter.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Message> exceptionHandler(ApiException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(
                        Message.FailureMessage.builder()
                        .errorCode(e.getError().getCode())
                        .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Message> exceptionHandler(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        Message.FailureMessage.builder()
                        .errorCode(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Message> exceptionHandler(AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        Message.FailureMessage.builder()
                                .errorCode(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> exceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        Message.FailureMessage.builder()
                                .errorCode(e.getMessage())
                                .build()
                );
    }
}
