package com.hmall.gateway.filters;

import com.hmall.common.exception.UnauthorizedException;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;

    private final JwtTool jwtTool;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取request对象
        ServerHttpRequest request = exchange.getRequest();
        // 判断是否做登陆校验
        if (isExcludePath(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = null;
        List<String> authorization = request.getHeaders().get("Authorization");
        if (authorization != null && !authorization.isEmpty()) {
            token = authorization.get(0);
        }
        // 获取用户信息
        Long userId = null;
        try {
             userId = jwtTool.parseToken(token);
        } catch (UnauthorizedException e) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 传递用户信息
        String userInfo = userId.toString();
        ServerWebExchange swe = exchange.mutate().request(
                builder -> builder
                        .header("user-info", userInfo)
                        ).build();

        return chain.filter(swe);
    }

    private boolean isExcludePath(String path) {
        for (String pathPattern : authProperties.getExcludePaths()){
            if (antPathMatcher.match(pathPattern, path)) {
                return true;
            }
        }
        return false;
    }
}
