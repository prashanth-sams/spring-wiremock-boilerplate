package com.wmock.info.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wmock.info.dto.Chapter4RequestDTO;
import com.wmock.info.dto.Chapter5RequestDTO;
import com.wmock.info.dto.Chapter6RequestDTO;
import com.wmock.info.dto.Chapter7RequestDTO;
import com.wmock.info.stubs.StubGenerator;
import com.wmock.info.tags.ApiTestOptimizedConfig;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;


@Log4j
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
        log.info("Stored stubbings: " + wireMockServer.getStubMappings().size());
        wireMockServer.resetAll();
        log.info("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
    }


    /**
     * generic post request with direct payload
     */
    @Test
    void withBodyRequestTest() {
        webTestClient
                .post()
                .uri(wireMockServer.baseUrl() + "/v1/newChapter3")
                .body(BodyInserters.fromObject("""
                    {
                      "chapterId": "3",
                      "name": "Luke"
                    }"""))
                .exchange()
                .expectStatus().isOk();
    }

    /**
     * post request with pojo for JSON payload
     */
    @Test
    void withPojoAsBodyRequestTest() {

        /* runtime setters */
        webTestClient
                .post()
                .uri(wireMockServer.baseUrl() + "/v1/newChapter4")
                .body(BodyInserters.fromValue(new Chapter4RequestDTO(4, "John")))
                .exchange()
                .expectStatus().isOk();

        /* manual setters */
        Chapter5RequestDTO chapter5RequestDTO = new Chapter5RequestDTO();
        chapter5RequestDTO.setChapterId(5);
        chapter5RequestDTO.setName("Acts");
        webTestClient
                .post()
                .uri(wireMockServer.baseUrl() + "/v1/newChapter5")
                .body(BodyInserters.fromValue(chapter5RequestDTO))
                .exchange()
                .expectStatus().isOk();

        /* avoid explicitly writing getter and setter methods in the DTO */
        Chapter6RequestDTO chapter6RequestDTO = new Chapter6RequestDTO();
        chapter6RequestDTO.setChapterId(6);
        chapter6RequestDTO.setName("Romans");
        webTestClient
                .post()
                .uri(wireMockServer.baseUrl() + "/v1/newChapter6")
                .body(BodyInserters.fromValue(chapter6RequestDTO))
                .exchange()
                .expectStatus().isOk();

//        /* using builder to set values in the DTO */
//        Chapter7RequestDTO chapter7RequestDTO = Chapter7RequestDTO
//                .builder()
//                .chapterId(7)
//                .name("Corinthians")
//                .build();
//
//        webTestClient
//                .post()
//                .uri(wireMockServer.baseUrl() + "/v1/newChapter7")
//                .body(BodyInserters.fromValue(chapter7RequestDTO.toString()))
//                .exchange()
//                .expectStatus().isOk();
    }

}
