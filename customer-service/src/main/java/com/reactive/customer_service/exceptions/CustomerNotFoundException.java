package com.reactive.customer_service.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    private static final String MASSAGE = "Customer [id=%d] is not found";

    public CustomerNotFoundException(Integer id){
        super(MASSAGE.formatted(id));
    }


}
