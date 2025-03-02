package hu.zoltan_sipeki.gateway.filter;

import java.net.URI;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTService;
import reactor.core.publisher.Mono;

@Component
public class JWTGlobalFilter implements GlobalFilter {

    @Autowired
    private JWTService jwtService;

    @SuppressWarnings("unchecked")
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var originalUri = (Set<URI>) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        
        for (var uri : originalUri) {
            var path = uri.getPath();
            if (path.startsWith("/order-service")) {
                return orderServiceFilter(exchange, chain);
            }

            if (path.startsWith("/catalog-service")) {
                return catalogServiceFilter(exchange, chain);
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> orderServiceFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return Filters.jwtFilter(jwtService, exchange, chain);
    }

    private Mono<Void> catalogServiceFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        if (request.getMethod().equals(HttpMethod.GET)) {
            return chain.filter(exchange);
        }

        return Filters.jwtFilter(jwtService, exchange, chain);
    }
}
