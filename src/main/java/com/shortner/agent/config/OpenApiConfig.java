package com.shortner.agent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI agentUrlShortenerOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Governed Agentic URL Shortener API")
                .version("v1")
                .description("API for URL shortening, click analytics, and governed workflow orchestration.")
                .license(new License().name("Internal use")));
    }
}
