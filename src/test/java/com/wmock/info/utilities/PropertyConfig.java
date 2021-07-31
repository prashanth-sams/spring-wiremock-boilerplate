package com.wmock.info.utilities;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wiremock")
public class PropertyConfig {
    private int port;

    public int getPort() {
        return port;
    }
}
