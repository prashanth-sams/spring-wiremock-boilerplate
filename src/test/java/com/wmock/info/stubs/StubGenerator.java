package com.wmock.info.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.wmock.info.utilities.WireMockInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.wmock.info.stubs.UrlMappingRules.patternForNewTestament;
import static org.apache.http.HttpStatus.SC_OK;

@ContextConfiguration(initializers = {WireMockInitializer.class})
public class StubGenerator {

    @Autowired WireMockServer wireMockServer;

    public void instantiateAll() {
        firstChapter();
        secondChapter();
        newTestamentChapter();
    }

    public StubMapping firstChapter() {
        return wireMockServer.stubFor(get(urlPathEqualTo("/v1/1"))
                .atPriority(1)
                .inScenario("responseBodyTest")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withStatus(SC_OK)
                    .withBody("{\n" +
                        "  \"chapterId\": \"1\",\n" +
                        "  \"name\": \"Genesis\"\n" +
                        "}")
                    .withFixedDelay(1000)
                )
        );
    }

    public StubMapping secondChapter() {
        return wireMockServer.stubFor(get(urlPathEqualTo("/v1/2"))
                .atPriority(2)
                .inScenario("responseBodyFileTest")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withStatus(SC_OK)
                    .withBodyFile("response/data.json")
                    .withFixedDelay(1000)
                )
        );
    }

    public StubMapping newTestamentChapter() {
        return wireMockServer.stubFor(get(patternForNewTestament())
                .inScenario("newTestamentChapter")
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(SC_OK)
                        .withBody("{\n" +
                            "  \"chapterId\": \"1\",\n" +
                            "  \"name\": \"Matthew\"\n" +
                            "}")
                        .withFixedDelay(1000)
                )
        );
    }
}
