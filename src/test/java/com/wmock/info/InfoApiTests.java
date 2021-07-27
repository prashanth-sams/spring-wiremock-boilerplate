package com.wmock.info;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Application.class)
@AutoConfigureWebTestClient(timeout = "25000")
class InfoApiTests {

	@Autowired
	private WebTestClient webTestClient;

	private static WireMockServer wireMockServer;

	@BeforeAll
	static void startWireMock() {
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(1111));
		wireMockServer.start();
	}

	@AfterAll
	static void stopWireMock() {
		wireMockServer.stop();
	}

	@BeforeEach
	void clearWireMock() {
		System.out.println("Stored stubbings: " + wireMockServer.getStubMappings().size());
		wireMockServer.resetAll();
		System.out.println("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
	}

	@Test
	void contextLoads() {
		wireMockServer.stubFor(
			WireMock.get("/api/chapter/1")
				.willReturn(aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody("{\n" +
								"  \"chapterId\": \"1\",\n" +
								"  \"name\": \"Genesis\"\n" +
								"}")
//						.withBodyFile("response/data.json")
						.withStatus(200)
						.withFixedDelay(1000)
				)
		);

		System.out.println(wireMockServer.baseUrl());
		assert(wireMockServer.isRunning());

		webTestClient
				.get()
				.uri("http://localhost:1111/api/chapter/1")
				.exchange()
				.expectStatus().isOk();
	}

}
