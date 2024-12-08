package com.reactive.customer_service.dto;

import com.reactive.customer_service.domain.Ticker;
import com.reactive.customer_service.domain.TradeAction;

public record StockTradeRequest(
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action
) {

    public Integer totalPrice(){
        return price * quantity;
    }
}
