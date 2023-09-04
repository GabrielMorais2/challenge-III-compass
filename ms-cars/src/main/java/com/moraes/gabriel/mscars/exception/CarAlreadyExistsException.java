package com.moraes.gabriel.mscars.exception;

public class CarAlreadyExistsException extends RuntimeException{
    public CarAlreadyExistsException(String message){
        super(message);
    }
}
