package com.reactive.customer_service.mapper;

import com.reactive.customer_service.domain.Ticker;
import com.reactive.customer_service.dto.CustomerInformation;
import com.reactive.customer_service.dto.Holding;
import com.reactive.customer_service.dto.StockTradeRequest;
import com.reactive.customer_service.dto.StockTradeResponse;
import com.reactive.customer_service.entity.Customer;
import com.reactive.customer_service.entity.PortfolioItem;

import java.util.List;

public class EntityDtoMapper {

    public static CustomerInformation toCustomerInformation(Customer customer,
                                                            List<PortfolioItem> items){
        var holdings = items.stream()
                .map(i -> new Holding(i.getTicker() , i.getQuantity()))
                .toList();

        return new CustomerInformation(
                customer.getId(),
                customer.getName(),
                customer.getBalance(),
                holdings
        );
    }

    public static PortfolioItem toPortfolioItem(Integer customerId , Ticker ticker){
        var portfolioItem = new PortfolioItem();
        portfolioItem.setCustomerId(customerId);;
        portfolioItem.setQuantity(0);
        portfolioItem.setTicker(ticker);
        return portfolioItem;
    }

    public static StockTradeResponse toStockTradeResponse(StockTradeRequest request , Integer customerId , Integer balance){
        return new StockTradeResponse(
                customerId,
                request.ticker(),
                request.price(),
                request.quantity(),
                request.action(),
                request.totalPrice(),
                balance
        );
    }

}
