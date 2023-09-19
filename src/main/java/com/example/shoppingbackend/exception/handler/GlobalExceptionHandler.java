package com.example.shoppingbackend.exception.handler;

import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorBody> handle(CustomerNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorBody body = new ErrorBody(status.value(), exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }

}
