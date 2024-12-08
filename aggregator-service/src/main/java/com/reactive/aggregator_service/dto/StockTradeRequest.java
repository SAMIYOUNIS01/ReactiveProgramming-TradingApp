package com.reactive.aggregator_service.dto;


import com.reactive.aggregator_service.domain.Ticker;
import com.reactive.aggregator_service.domain.TradeAction;

public record StockTradeRequest(
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action
) {

}
