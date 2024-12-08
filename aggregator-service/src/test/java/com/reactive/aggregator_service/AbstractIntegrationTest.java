package com.reactive.aggregator_service;

import org.mockserver.client.MockServerClient;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(properties = {
        "stock.service.url=http://localhost:${mockServerPort}",
        "customer.service.url=http://localhost:${mockServerPort}"
})
@AutoConfigureWebTestClient
@MockServerTest
abstract class AbstractIntegrationTest {

    // it is set by @Mock annotation automatically
    protected MockServerClient mockServerClient;

    @Autowired
    protected WebTestClient client;




}
