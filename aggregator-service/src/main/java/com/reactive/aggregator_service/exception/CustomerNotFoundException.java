package com.reactive.aggregator_service.exception;

public class CustomerNotFoundException extends RuntimeException{

    private static final String MASSAGE = "Customer [id=%d] is not found";

    public CustomerNotFoundException(Integer id){
        super(MASSAGE.formatted(id));
    }


}
