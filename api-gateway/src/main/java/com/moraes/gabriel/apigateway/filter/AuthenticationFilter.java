package com.moraes.gabriel.apigateway.filter;

import com.moraes.gabriel.apigateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;


    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (validator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                   return onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    jwtUtil.validateToken(authHeader);

                    request = exchange.getRequest()
                            .mutate()
                            .header("idUser", jwtUtil.extractUsername(authHeader))
                            .build();

                } catch (Exception e) {
                    return onError(exchange, "Un authorized access to application", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return  response.setComplete();
    }

    public static class Config {

    }



}
