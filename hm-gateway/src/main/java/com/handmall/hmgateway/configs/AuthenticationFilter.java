package com.handmall.hmgateway.configs;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator routeValidator;
    private final JwtUtil jwtUtil;
    public AuthenticationFilter(RouteValidator routeValidator, JwtUtil jwtUtil) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //check if header contains token
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    //REST call to AUTH service
                    //template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);
                } catch (Exception ex) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("an unauthorized access");
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config {}
}
