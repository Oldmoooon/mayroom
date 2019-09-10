package name.guyue.backend.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hujia
 * @date 2019-03-25
 */
@Configuration
@Slf4j
public class SessionConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            //排除拦截
            .excludePathPatterns("/user/login")
            .excludePathPatterns("/user/admin/login")
            //拦截路径
            .addPathPatterns("/**");
    }

    public static class LoginInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            var session = request.getSession();
            Object userId = session.getAttribute(session.getId());
            log.info("user {} assessed: {}.", userId, request.getRequestURL());
            if (userId != null) {
                return true;
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }
    }
}
