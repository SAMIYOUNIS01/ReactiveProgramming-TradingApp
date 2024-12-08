package com.reactive.customer_service.dto;

import com.reactive.customer_service.domain.Ticker;

public record Holding(Ticker ticker, Integer quantity) {
}
