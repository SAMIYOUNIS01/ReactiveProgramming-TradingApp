package com.reactive.aggregator_service;

import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;


public class CustomerInformationTest extends AbstractIntegrationTest{

    private static final Logger log = LoggerFactory.getLogger(CustomerInformationTest.class);

    @Test
    public void customerInformation(){
        // mock customer service
        var responseBody = """
                {
                "name": "sam"
                }
                """;
        this.mockServerClient
                .when(HttpRequest.request("/customer/1"))
                .respond(
                        HttpResponse.response(responseBody)
                                .withStatusCode(200)
                                .withContentType(MediaType.APPLICATION_JSON)
                );

        this.client.get()
                .uri("/customers/1")
                .exchange()
                .expectBody()
                .consumeWith(e -> log.info("{}" , new String(Objects.requireNonNull(e.getResponseBody()))));
    }
}
