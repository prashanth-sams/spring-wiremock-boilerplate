package com.wmock.info.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wmock.info.stubs.StubGenerator;
import com.wmock.info.tags.ApiTestOptimizedConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.apache.log4j.Logger;


@ApiTestOptimizedConfig
class PojoPayloadTests extends StubGenerator {
    private static final Logger LOGGER = Logger.getLogger(PojoPayloadTests.class.getName());

    @Autowired WebTestClient webTestClient;

    @Autowired WireMockServer wireMockServer;

    @BeforeEach
    void initStubs() {
        clearWireMock();
        instantiateAll();
    }

    void clearWireMock() {
        LOGGER.info("Stored stubbings: " + wireMockServer.getStubMappings().size());
        wireMockServer.resetAll();
        LOGGER.info("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
    }

    @Test
    void withBodyRequestTest() {
        webTestClient
            .post()
            .uri(wireMockServer.baseUrl() + "/v1/newChapter3")
            .body(BodyInserters.fromObject("{\n" +
                    "  \"chapterId\": \"3\",\n" +
                    "  \"name\": \"Luke\"\n" +
                    "}"))
            .exchange()
            .expectStatus().isOk();
    }

}
