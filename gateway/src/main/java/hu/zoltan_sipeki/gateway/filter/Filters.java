package hu.zoltan_sipeki.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTService;
import reactor.core.publisher.Mono;

public class Filters {

    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER = "Bearer ";

    public static Mono<Void> jwtFilter(JWTService jwtService, ServerWebExchange exchange,
            GatewayFilterChain chain) {
        var request = exchange.getRequest();
        var bearer = request.getHeaders().get(AUTHORIZATION);
        if (bearer == null || bearer.isEmpty() || !bearer.get(0).startsWith(BEARER)) {
            return send401(exchange);
        }

        var token = bearer.get(0).substring(BEARER.length());
        if (jwtService.verifyToken(token) != null) {
            return chain.filter(exchange);
        }

        return send401(exchange);
    }

    public static Mono<Void> send401(ServerWebExchange exchange) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}
