package com.moraes.gabriel.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/v1/users/login",
            "/v1/users/register",
            "/v1/users/validate",
            "/eureka",
            "/ms-cars/v3/api-docs",
            "/ms-races/v3/api-docs",
            "/ms-history/v3/api-docs",
            "/ms-users/v3/api-docs"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
