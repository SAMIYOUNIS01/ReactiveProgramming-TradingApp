package com.reactive.customer_service.service;

import com.reactive.customer_service.dto.StockTradeRequest;
import com.reactive.customer_service.dto.StockTradeResponse;
import com.reactive.customer_service.entity.Customer;
import com.reactive.customer_service.entity.PortfolioItem;
import com.reactive.customer_service.exceptions.ApplicationException;
import com.reactive.customer_service.mapper.EntityDtoMapper;
import com.reactive.customer_service.repository.CustomerRepository;
import com.reactive.customer_service.repository.PortfolioItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class TradeService {

    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public TradeService(CustomerRepository customerRepository, PortfolioItemRepository portfolioItemRepository) {
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    @Transactional
    public Mono<StockTradeResponse> trade (Integer customerId , StockTradeRequest request){
        return switch (request.action()){
            case BUY -> this.buyStock(customerId , request);
            case SELL -> this.sellStock(customerId , request);
        };
    }

    private Mono<StockTradeResponse> sellStock(Integer customerId, StockTradeRequest request) {
        var customerMono = this.customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationException.customerNotFound(customerId));

        var portfolioItemMono = this.portfolioItemRepository.findByCustomerIdAndTicker(customerId , request.ticker())
                .filter(pi -> pi.getQuantity() >= request.quantity())
                .switchIfEmpty(ApplicationException.insuffiecientShares(customerId));

        return customerMono.zipWhen(customer -> portfolioItemMono)
                .flatMap(t -> this.executeSell(t.getT1() , t.getT2() , request));

    }
    private Mono<StockTradeResponse> executeSell(Customer customer, PortfolioItem portfolioItem , StockTradeRequest request){
        customer.setBalance(customer.getBalance() + request.totalPrice());
        portfolioItem.setQuantity(portfolioItem.getQuantity() - request.quantity());
        return this.saveAndBuildResponse(customer , portfolioItem , request);
    }

    private Mono<StockTradeResponse> saveAndBuildResponse(Customer customer, PortfolioItem portfolioItem , StockTradeRequest request){
        var response = EntityDtoMapper.toStockTradeResponse(request , customer.getId() , customer.getBalance());
        return Mono.zip(this.customerRepository.save(customer) , this.portfolioItemRepository.save(portfolioItem))
                .thenReturn(response);
    }


        private Mono<StockTradeResponse> buyStock(Integer customerId, StockTradeRequest request) {
        var customerMono = this.customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationException.customerNotFound(customerId))
                .filter(c -> c.getBalance() >= request.totalPrice())
                .switchIfEmpty(ApplicationException.insufficientBalance(customerId));

        var portfolioItemMono = this.portfolioItemRepository.findByCustomerIdAndTicker(customerId , request.ticker())
                .defaultIfEmpty(EntityDtoMapper.toPortfolioItem(customerId , request.ticker()));

        return customerMono.zipWhen(customer -> portfolioItemMono)
                .flatMap(t -> this.executeBuy(t.getT1() , t.getT2() , request));



    }

    private Mono<StockTradeResponse> executeBuy(Customer customer, PortfolioItem portfolioItem , StockTradeRequest request){
        customer.setBalance(customer.getBalance() - request.totalPrice());
        portfolioItem.setQuantity(portfolioItem.getQuantity() + request.quantity());
        return this.saveAndBuildResponse(customer , portfolioItem , request);
    }
}
