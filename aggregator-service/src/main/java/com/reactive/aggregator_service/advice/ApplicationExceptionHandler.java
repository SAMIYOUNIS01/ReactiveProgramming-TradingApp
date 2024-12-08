package com.reactive.aggregator_service.advice;

import com.reactive.aggregator_service.exception.CustomerNotFoundException;
import com.reactive.aggregator_service.exception.InvalidTradeRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException exception){
        return build(HttpStatus.NOT_FOUND, exception,problem->{
            problem.setType(URI.create("http://example.com/problems/customer-not-found"));
            problem.setTitle("Customer Not Found");
        });
    }
    @ExceptionHandler(InvalidTradeRequestException.class)
    public ProblemDetail handleException(InvalidTradeRequestException exception){
        return build(HttpStatus.BAD_REQUEST, exception,problem->{
            problem.setType(URI.create("http://example.com/problems/insufficient-shares"));
            problem.setTitle("Invalid Trade Request");
        });
    }


    private ProblemDetail build(HttpStatus status, Exception ex , Consumer<ProblemDetail> consumer){
        var problem = ProblemDetail.forStatusAndDetail(status , ex.getMessage());
        consumer.accept(problem);
        return problem;
    }
}
