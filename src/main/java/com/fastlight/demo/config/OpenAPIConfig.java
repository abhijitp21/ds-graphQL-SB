package com.fastlight.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "FastLight API",
                version = "1.0",
                description = "API documentation for FastLight"
        ),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenAPIConfig {
}