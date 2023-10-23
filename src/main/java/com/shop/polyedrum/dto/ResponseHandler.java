package com.shop.polyedrum.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler{
    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object responseObj){
        Map<String, Object> response = new HashMap<>();

        response.put("message", message);
        response.put("http", httpStatus);
        response.put("data", responseObj);

        return new ResponseEntity<>(response, httpStatus);
    }
}
