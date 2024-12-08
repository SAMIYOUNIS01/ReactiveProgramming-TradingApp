package com.reactive.customer_service.dto;

import com.reactive.customer_service.domain.Ticker;
import com.reactive.customer_service.domain.TradeAction;

public record StockTradeResponse(
        Integer customerId,
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action,
        Integer totalPrice,
        Integer balance
) {
}
