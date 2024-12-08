package com.reactive.aggregator_service.service;

import com.reactive.aggregator_service.client.CustomerServiceClient;
import com.reactive.aggregator_service.client.StockServiceClient;
import com.reactive.aggregator_service.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerPortfolioService {

    private final StockServiceClient stockServiceClient;
    private final CustomerServiceClient customerServiceClient;

    public CustomerPortfolioService(StockServiceClient stockServiceClient, CustomerServiceClient customerServiceClient) {
        this.stockServiceClient = stockServiceClient;
        this.customerServiceClient = customerServiceClient;
    }


    public Mono<CustomerInformation> getCustomerInformation(Integer customerId){
        return this.customerServiceClient.getCustomerInformation(customerId);
    }

    public Mono<StockTradeResponse> trade(Integer customerId , TradeRequest request){
        return this.stockServiceClient.getStockPrice(request.ticker())
                .map(StockPriceResponse::price)
                .map(price -> this.toStockTradeRequest(request , price))
                .flatMap(req -> this.customerServiceClient.trade(customerId , req));
    }

    private StockTradeRequest toStockTradeRequest(TradeRequest request , Integer price){
        return new StockTradeRequest(
                request.ticker(),
                price,
                request.quantity(),
                request.action()
        );
    }
}