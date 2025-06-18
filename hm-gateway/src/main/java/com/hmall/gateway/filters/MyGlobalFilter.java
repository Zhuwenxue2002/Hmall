package com.hmall.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class MyGlobalFilter implements GlobalFilter, Ordered {
    /**
     * 实现全局过滤器接口的方法
     *
     * @param exchange 登陆上下文，用以获取参数
     * @param chain    网关过滤器链，当前过滤器执行完之后放行
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 模拟登陆校验
        return chain.filter(exchange);
    }

    /**
     * 实现排序接口方法，设置过滤器优先级
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
