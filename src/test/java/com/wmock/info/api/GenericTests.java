package com.wmock.info.api;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.wmock.info.tags.ApiTestConfiguration;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApiTestConfiguration
class GenericTests {
    private static final Logger LOGGER = Logger.getLogger(GenericTests.class.getName());

    @Autowired
    private WebTestClient webTestClient;

    private WireMockServer wireMockServer;

    @Value("${wiremock.port:2222}")
    private Integer wmPort;

    @BeforeAll
    void startWireMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(wmPort));
        wireMockServer.start();
    }

    @AfterAll
    void stopWireMock() {
        wireMockServer.stop();
    }

    void clearWireMock() {
        LOGGER.info("Stored stubbings: " + wireMockServer.getStubMappings().size());
        wireMockServer.resetAll();
        LOGGER.info("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
    }

    @BeforeEach
    void initStubs() {
        clearWireMock();

        wireMockServer.stubFor(
            get("/v1/1")
                .inScenario("responseBodyTest")
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                            {
                              "chapterId": "1",
                              "name": "Genesis"
                            }""")
                        .withStatus(200)
                        .withFixedDelay(1000)
                )
        );

        wireMockServer.stubFor(
            get("/v1/2")
                .inScenario("responseBodyFileTest")
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("response/data.json")
                        .withStatus(200)
                        .withFixedDelay(1000)
                )
        );

        wireMockServer.stubFor(
            post("/v1/newChapter")
                .inScenario("responseStatusPostTest")
                .willReturn(unauthorized())
        );

        LOGGER.info(wireMockServer.port());
        LOGGER.info(wireMockServer.baseUrl());
        assert (wireMockServer.isRunning());
    }

    @Test
    void responseBodyTest() {
        webTestClient
            .get().uri(wireMockServer.baseUrl() + "/v1/1")
            .exchange()
            .expectStatus().isOk();

        Response resp = given().log().all()
            .when()
            .get(wireMockServer.baseUrl() + "/v1/1");
        assertEquals(resp.getStatusCode(), 200);

        var body = resp.getBody();
        assertEquals(body.asString(), """
                {
                  "chapterId": "1",
                  "name": "Genesis"
                }""");
    }

    @Test
    void responseBodyFileTest() {
      webTestClient
          .get().uri(wireMockServer.baseUrl() + "/v1/2")
          .exchange()
          .expectStatus().isOk();

      given()
          .when()
          .get(wireMockServer.baseUrl() + "/v1/2")
          .then()
          .body("name", response -> containsString("Exodus"));
    }

    @Test
    void responseStatusPostTest() {
      webTestClient
          .post().uri(wireMockServer.baseUrl() + "/v1/newChapter")
          .exchange()
          .expectStatus().isUnauthorized();

      Response resp = given().log().all()
          .when()
          .post(wireMockServer.baseUrl() + "/v1/newChapter");
      assertEquals(resp.getStatusCode(), 401);
    }

}
