package com.shop.polyedrum.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus){
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
