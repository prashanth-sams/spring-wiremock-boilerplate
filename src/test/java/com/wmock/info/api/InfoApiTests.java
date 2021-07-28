package com.wmock.info.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.wmock.info.tags.ApiTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ApiTest
class InfoApiTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private WireMockServer wireMockServer;

	@AfterAll
	void stopWireMock() {
		wireMockServer.stop();
	}

	@BeforeEach
	void clearWireMock() {
		System.out.println("Stored stubbings: " + wireMockServer.getStubMappings().size());
		wireMockServer.resetAll();
		System.out.println("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
	}

	@Test
	void responseBodyTest() {
		wireMockServer.stubFor(
			WireMock.get("/api/chapter/1")
				.willReturn(aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody("{\n" +
								"  \"chapterId\": \"1\",\n" +
								"  \"name\": \"Genesis\"\n" +
								"}")
						.withStatus(200)
						.withFixedDelay(1000)
				)
		);

		System.out.println(wireMockServer.baseUrl());
		assert(wireMockServer.isRunning());

		webTestClient
				.get()
				.uri(wireMockServer.baseUrl()+"/api/chapter/1")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void responseBodyFileTest() {
		wireMockServer.stubFor(
				WireMock.get("/api/chapter/1")
						.willReturn(aResponse()
										.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
										.withBodyFile("response/data.json")
										.withStatus(200)
										.withFixedDelay(1000)
						)
		);

		System.out.println(wireMockServer.baseUrl());
		assert(wireMockServer.isRunning());

		webTestClient
				.get()
				.uri(wireMockServer.baseUrl()+"/api/chapter/1")
				.exchange()
				.expectStatus().isOk();
	}

}
