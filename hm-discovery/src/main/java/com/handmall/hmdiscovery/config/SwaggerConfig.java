package com.handmall.hmdiscovery.config;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class SwaggerConfig {
    private final DiscoveryClient discoveryClient;

    public SwaggerConfig(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/swagger-config.json")
    public Map<String, Object> swaggerConfig() {
        List<SwaggerUrl> urls = new LinkedList<>();
        discoveryClient.getServices().forEach(serviceName ->
                discoveryClient.getInstances(serviceName)
                        .forEach(serviceInstance -> urls.add(new SwaggerUrl("serviceName", serviceInstance.getUri() + "/v3/api-docs", "serviceName")))
        );
        return Map.of("urls", urls);
    }
}
