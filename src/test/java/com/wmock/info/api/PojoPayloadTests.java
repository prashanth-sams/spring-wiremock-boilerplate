package com.wmock.info.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wmock.info.stubs.StubGenerator;
import com.wmock.info.tags.ApiTestConfiguration;
import com.wmock.info.tags.ApiTestOptimizedConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTestOptimizedConfig
class PojoPayloadTests extends StubGenerator {

    @Autowired WebTestClient webTestClient;

    @Autowired WireMockServer wireMockServer;

    @BeforeEach
    void initStubs() {
        clearWireMock();
        instantiateAll();
    }

    void clearWireMock() {
        System.out.println("Stored stubbings: " + wireMockServer.getStubMappings().size());
        wireMockServer.resetAll();
        System.out.println("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
    }

    @Test
    void responseStatusPostTest() {
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
