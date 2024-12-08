package com.reactive.customer_service.service;

import com.reactive.customer_service.dto.CustomerInformation;
import com.reactive.customer_service.entity.Customer;
import com.reactive.customer_service.exceptions.ApplicationException;
import com.reactive.customer_service.mapper.EntityDtoMapper;
import com.reactive.customer_service.repository.CustomerRepository;
import com.reactive.customer_service.repository.PortfolioItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public CustomerService(CustomerRepository customerRepository, PortfolioItemRepository portfolioItemRepository) {
        this.customerRepository = customerRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId){
       return this.customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationException.customerNotFound(customerId))
                .flatMap(this::buildCustoemrInformation);
    }

    private Mono<CustomerInformation> buildCustoemrInformation(Customer customer){
        return this.portfolioItemRepository.findAllByCustomerId(customer.getId())
                .collectList()
                .map(list -> EntityDtoMapper.toCustomerInformation(customer , list));
    }
}
