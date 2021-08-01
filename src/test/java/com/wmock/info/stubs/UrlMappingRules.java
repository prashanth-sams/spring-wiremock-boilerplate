package com.wmock.info.stubs;

import com.github.tomakehurst.wiremock.matching.UrlPattern;

import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

public class UrlMappingRules {

    public static UrlPattern patternForNewTestament() {
        return urlMatching("/v2/.*");
    }
}
