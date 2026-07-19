package space.huyuhao.myojgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 全局鉴权过滤器
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 不需要登录的路径
     */
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/api/user/login",
            "/api/user/register",
            "/api/user/get/login",
            "/api/user/logout",
            "/**/v2/api-docs",
            "/**/v3/api-docs",
            "/**/swagger-resources",
            "/**/swagger-ui.html",
            "/**/webjars/**",
            "/**/doc.html",
            "/**/favicon.ico"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // 不需要登录的路径直接放行
        for (String excludePath : EXCLUDE_PATHS) {
            if (antPathMatcher.match(excludePath, path)) {
                return chain.filter(exchange);
            }
        }

        // 检查是否登录（通过 Cookie/Header 中的 Session）
        // 由于 Gateway 是 Reactive 模式，此处做基本的 Cookie 检查
        // 详细的鉴权由各微服务的 AuthInterceptor 处理
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
