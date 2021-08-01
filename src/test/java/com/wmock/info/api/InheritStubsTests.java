package com.wmock.info.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wmock.info.stubs.StubGenerator;
import com.wmock.info.tags.ApiTestOptimizedConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@ApiTestOptimizedConfig
class InheritStubsTests extends StubGenerator {

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
	void inheritStubTest() {
		webTestClient
			.get()
			.uri(wireMockServer.baseUrl()+"/v1/2")
			.exchange()
			.expectStatus().isOk()
			.expectStatus().is2xxSuccessful()
			.expectBody().jsonPath("name").isEqualTo("Exodus");
	}

	@Test
	void inheritStubWithPatternTest() {
		webTestClient
			.get()
			.uri(wireMockServer.baseUrl()+"/v2/2")
			.exchange()
			.expectStatus().isOk()
			.expectStatus().is2xxSuccessful()
			.expectBody().jsonPath("name").isEqualTo("Matthew");
	}

}
