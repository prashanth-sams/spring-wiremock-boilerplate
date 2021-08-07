package com.wmock.info.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wmock.info.tags.ApiTestOptimizedConfig;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;


@Log4j
@ApiTestOptimizedConfig
class OptimizedTests {

	@Autowired WebTestClient webTestClient;

	@Autowired WireMockServer wireMockServer;

	@BeforeEach
	void initStubs() {
		clearWireMock();

		wireMockServer.stubFor(
			get("/v1/1")
				.inScenario("responseBodyTest")
				.willReturn(aResponse()
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
				.willReturn(aResponse()
					.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.withBodyFile("response/data.json")
					.withStatus(200)
					.withFixedDelay(1000)
				)
		);

		log.info(wireMockServer.port());
		log.info(wireMockServer.baseUrl());

	}

	void clearWireMock() {
		log.info("Stored stubbings: " + wireMockServer.getStubMappings().size());
		wireMockServer.resetAll();
		log.info("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
	}

	@Test
	void responseBodyFileTest() {
		webTestClient
			.get()
			.uri(wireMockServer.baseUrl()+"/v1/2")
			.exchange()
			.expectStatus().isOk()
			.expectStatus().is2xxSuccessful()
			.expectBody().jsonPath("name").isEqualTo("Exodus");
	}

}
