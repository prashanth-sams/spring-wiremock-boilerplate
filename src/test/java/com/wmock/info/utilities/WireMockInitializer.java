package com.wmock.info.utilities;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.env.ConfigurableEnvironment;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private WireMockServer wireMockServer;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        /**
         * return custom port from the property file
         * Follow this for dynamic port handling
         * wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
         */
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        BindResult<PropertyConfig> configProps = Binder.get(environment).bind("wiremock", PropertyConfig.class);

        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(configProps.get().getPort()));
        wireMockServer.start();

        configurableApplicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                wireMockServer.stop();
            }
        });

        configurableApplicationContext.getBeanFactory()
                .registerSingleton("wireMockServer", wireMockServer);
    }
}
