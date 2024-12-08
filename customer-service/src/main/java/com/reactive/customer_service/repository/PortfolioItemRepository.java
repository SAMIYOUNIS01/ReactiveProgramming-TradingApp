package com.reactive.customer_service.repository;

import com.reactive.customer_service.domain.Ticker;
import com.reactive.customer_service.entity.PortfolioItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.sound.sampled.Port;

@Repository
public interface PortfolioItemRepository extends ReactiveCrudRepository<PortfolioItem , Integer> {

    Flux<PortfolioItem> findAllByCustomerId(Integer customerId);
    Mono<PortfolioItem> findByCustomerIdAndTicker(Integer customerId , Ticker ticker);
}
