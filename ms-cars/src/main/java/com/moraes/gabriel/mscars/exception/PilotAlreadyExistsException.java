package com.moraes.gabriel.mscars.exception;

public class PilotAlreadyExistsException extends RuntimeException{
    public PilotAlreadyExistsException(String message){
        super(message);
    }
}
