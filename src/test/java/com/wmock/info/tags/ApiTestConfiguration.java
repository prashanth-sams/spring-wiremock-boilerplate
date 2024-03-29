package com.wmock.info.tags;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import com.wmock.info.Application;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Tag("ApiTestConfiguration")
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//        properties = { "server.port=2222"})
@AutoConfigureWebTestClient(timeout = "25000")
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public @interface ApiTestConfiguration {
}
