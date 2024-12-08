package com.reactive.aggregator_service.exception;

public class InvalidTradeRequestException extends RuntimeException {

    public InvalidTradeRequestException(String message){
        super(message);
    }
}
