package com.reactive.aggregator_service.dto;

import com.reactive.aggregator_service.domain.Ticker;

import java.time.LocalDateTime;

public record StockPriceResponse(Ticker ticker,
                                 Integer price) {
}
