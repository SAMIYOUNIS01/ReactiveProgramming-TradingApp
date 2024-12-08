package com.reactive.aggregator_service.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.reactive.aggregator_service.dto.CustomerInformation;
import com.reactive.aggregator_service.dto.StockTradeResponse;
import com.reactive.aggregator_service.dto.TradeRequest;
import com.reactive.aggregator_service.service.CustomerPortfolioService;
import com.reactive.aggregator_service.validator.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerPortfolioContorller {

    private static final Logger log = LoggerFactory.getLogger(CustomerPortfolioContorller.class);

    private final CustomerPortfolioService customerPortfolioService;

    public CustomerPortfolioContorller(CustomerPortfolioService customerPortfolioService) {
        this.customerPortfolioService = customerPortfolioService;
    }

    @GetMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId){
        return this.customerPortfolioService.getCustomerInformation(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable Integer customerId, @RequestBody Mono<TradeRequest> mono){
        log.info("received: {}", customerId);
        return mono.transform(RequestValidator.validate())
                .flatMap(req -> this.customerPortfolioService.trade(customerId , req));
    }
}
