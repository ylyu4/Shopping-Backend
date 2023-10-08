package com.example.shoppingbackend.exception.handler;

import com.example.shoppingbackend.exception.CustomerNotFoundException;
import com.example.shoppingbackend.exception.ErrorBody;
import com.example.shoppingbackend.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class,
            ProductNotFoundException.class})
    public ResponseEntity<ErrorBody> handle(Exception exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorBody body = new ErrorBody(status.value(), exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }

}
