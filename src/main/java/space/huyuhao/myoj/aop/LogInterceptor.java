package space.huyuhao.myoj.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

/**
 * 请求日志 AOP
 */
@Aspect
@Component
@Slf4j
public class LogInterceptor {

    @Around("execution(* space.huyuhao.myoj.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        // 生成唯一请求 ID
        String requestId = UUID.randomUUID().toString();
        // 获取请求信息
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        // 打印请求开始日志
        log.info("request start, id: {}, path: {}, ip: {}, params: {}",
                requestId, url, ip, joinPoint.getArgs());
        long startTime = System.currentTimeMillis();
        // 执行原方法
        Object result = joinPoint.proceed();
        // 打印请求结束日志
        long duration = System.currentTimeMillis() - startTime;
        log.info("request end, id: {}, duration: {}ms", requestId, duration);
        return result;
    }
}
