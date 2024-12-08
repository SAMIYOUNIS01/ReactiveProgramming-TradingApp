package com.reactive.aggregator_service.dto;


import com.reactive.aggregator_service.domain.Ticker;

public record Holding(Ticker ticker, Integer quantity) {
}
