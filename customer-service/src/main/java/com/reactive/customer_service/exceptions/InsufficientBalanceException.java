package com.reactive.customer_service.exceptions;

public class InsufficientBalanceException extends RuntimeException {

    private static final String MASSAGE = "Customer [id=%d] does not have enough funds to complete the transaction";

    public InsufficientBalanceException(Integer id ){
        super(MASSAGE.formatted(id));
    }
}
