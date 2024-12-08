package com.reactive.aggregator_service.validator;

import com.reactive.aggregator_service.dto.TradeRequest;
import com.reactive.aggregator_service.exception.ApplicationException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<TradeRequest>> validate(){
        return mono -> mono.filter(hasTicker())
                .switchIfEmpty(ApplicationException.missingTicker())
                .filter(hasTradeAction())
                .switchIfEmpty(ApplicationException.missingTradeAction())
                .filter(isValidQuantity())
                .switchIfEmpty(ApplicationException.invalidQuantity());
    }

    private static Predicate<TradeRequest> hasTicker(){
        return dto -> Objects.nonNull(dto.ticker());
    }
    private static Predicate<TradeRequest> hasTradeAction(){
        return dto -> Objects.nonNull(dto.action());
    }
    private static Predicate<TradeRequest> isValidQuantity(){
        return dto -> Objects.nonNull(dto.quantity()) && dto.quantity() > 0;
    }
}
