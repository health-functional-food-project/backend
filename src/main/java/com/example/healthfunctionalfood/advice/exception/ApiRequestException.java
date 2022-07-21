package com.example.healthfunctionalfood.advice.exception;

public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String message){
        super(message);
    }
}
