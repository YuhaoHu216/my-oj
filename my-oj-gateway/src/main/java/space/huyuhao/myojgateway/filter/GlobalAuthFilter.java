package space.huyuhao.myojgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import space.huyuhao.myojcommon.utils.JwtUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 全局鉴权过滤器
 * 在 Gateway 层统一解析 JWT，将用户信息通过 Header 传递给下游微服务
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GlobalAuthFilter.class);

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private JwtUtil jwtUtil;

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

        // 解析 JWT Token
        String token = extractToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            // 未登录或 token 无效，返回 401
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            DataBufferFactory bufferFactory = response.bufferFactory();
            DataBuffer buffer = bufferFactory.wrap(
                    "{\"code\":401,\"message\":\"未登录或登录已过期\"}".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }

        // 从 Token 中提取用户信息
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);

        // 将用户信息通过 Header 传递给下游微服务
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", userId != null ? userId.toString() : "")
                .header("X-Username", username != null ? username : "")
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    /**
     * 从请求中提取 JWT Token
     */
    private String extractToken(ServerHttpRequest request) {
        // 优先从 Authorization Header 获取
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        // 其次从请求参数中获取
        String tokenParam = request.getQueryParams().getFirst("token");
        if (tokenParam != null) {
            return tokenParam;
        }

        return null;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
