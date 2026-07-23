package space.huyuhao.myojquestion.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import space.huyuhao.myojquestion.context.UserContext;

/**
 * 用户上下文拦截器
 * 从 Gateway 传递的 Header 中读取用户信息并设置到 UserContext
 */
@Component
public class UserContextInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserContextInterceptor.class);

    private static final String HEADER_USER_ID = "X-User-Id";
    private static final String HEADER_USERNAME = "X-Username";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userIdStr = request.getHeader(HEADER_USER_ID);
        String username = request.getHeader(HEADER_USERNAME);

        if (StringUtils.hasText(userIdStr)) {
            try {
                UserContext.setUserId(Long.valueOf(userIdStr));
            } catch (NumberFormatException e) {
                log.warn("Invalid X-User-Id header: {}", userIdStr);
            }
        }

        if (StringUtils.hasText(username)) {
            UserContext.setUsername(username);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        UserContext.clear();
    }
}
