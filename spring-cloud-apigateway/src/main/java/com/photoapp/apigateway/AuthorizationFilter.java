package com.photoapp.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
@SuppressWarnings("rawtypes")
public class AuthorizationFilter extends AbstractGatewayFilterFactory {

	@Autowired
	Environment env;

	public AuthorizationFilter() {
		super(Config.class);
	}

	public static class Config{
		
	}
	@Override
	public GatewayFilter apply(Object config) {

		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			if (!request.getHeaders().containsKey("Authorization"))
				return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
			String authHeader = request.getHeaders().get("Authorization").get(0);
			String jwt = authHeader.replace("barier", "");
			if (!isJWTValid(jwt)) {
				return onError(exchange, "Jwt token is not valid", HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String string, HttpStatus unauthorized) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(unauthorized);
		return response.setComplete();
	}

	private boolean isJWTValid(String jwt) {
		boolean retrurnValue = true;
		String subject = Jwts.parser().setSigningKey("token-secret").parseClaimsJws(jwt).getBody().getSubject();
		if (subject == null || subject.isEmpty())
			return false;
		return retrurnValue;

	}
}
