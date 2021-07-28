package com.wmock.info.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wmock.info.tags.ApiTest;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import io.restassured.response.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTest
class InfoApiTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private WireMockServer wireMockServer;

	@BeforeEach
	void initStubs() {
		clearWireMock();

		wireMockServer.stubFor(
			get("/v1/1")
				.inScenario("responseBodyTest")
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

	}

	void clearWireMock() {
		System.out.println("Stored stubbings: " + wireMockServer.getStubMappings().size());
		wireMockServer.resetAll();
		System.out.println("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
	}

	@Test
	void responseBodyTest() {
		System.out.println(wireMockServer.baseUrl());
		assert(wireMockServer.isRunning());

		webTestClient
			.get()
			.uri(wireMockServer.baseUrl()+"/v1/1")
			.exchange()
			.expectStatus().isOk();

		Response resp = given().log().all()
				.when().get(wireMockServer.baseUrl()+"/v1/1");
		assertEquals(resp.getStatusCode(), 200);

		ResponseBody body = resp.getBody();
		assertEquals(body.asString(), "{\n" +
				"  \"chapterId\": \"1\",\n" +
				"  \"name\": \"Genesis\"\n" +
				"}");
	}

	@Test
	void responseBodyFileTest() {
		System.out.println(wireMockServer.baseUrl());
		System.out.println(wireMockServer.port());

		webTestClient
			.get()
			.uri(wireMockServer.baseUrl()+"/v1/2")
			.exchange()
			.expectStatus().isOk();

		given()
		.when()
			.get(wireMockServer.baseUrl()+"/v1/2")
		.then()
			.body("name", response -> containsString("Exodus"));
	}

}
