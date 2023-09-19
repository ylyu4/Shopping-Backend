package com.example.shoppingbackend.exception;

public class ErrorBody {

    private Integer status;

    private String message;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ErrorBody(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
