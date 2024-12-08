package com.reactive.customer_service.exceptions;

public class InsufficientSharesException extends RuntimeException {

    private static final String MASSAGE = "Customer [id=%d] does not have enough shares to complete the transaction";

    public InsufficientSharesException(Integer id ){
        super(MASSAGE.formatted(id));
    }
}
