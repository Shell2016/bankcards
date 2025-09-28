package com.example.bankcards.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenApiGenerator {

    private final Environment environment;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    @EventListener(ApplicationReadyEvent.class)
    public void generateOpenApiFile() {
        try {
            Thread.sleep(5000);
            String port = environment.getProperty("server.port", "8080");
            String apiDocsUrl = "http://localhost:" + port + "/v3/api-docs";

            log.info("Generating OpenAPI file from: {}", apiDocsUrl);

            String jsonResponse = restTemplate.getForObject(apiDocsUrl, String.class);

            if (jsonResponse != null) {
                Files.createDirectories(Paths.get("docs"));
                Object jsonObject = new ObjectMapper().readValue(jsonResponse, Object.class);
                String yamlContent = yamlMapper.writeValueAsString(jsonObject);
                Files.write(Paths.get("docs/openapi.yaml"), yamlContent.getBytes());
                log.info("OpenAPI file generated successfully: docs/openapi.yaml");
            } else {
                log.warn("Failed to get API docs from {}", apiDocsUrl);
            }

        } catch (Exception e) {
            log.error("Error generating OpenAPI file", e);
        }
    }
}
