package name.guyue.backend.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hujia
 * @date 2019-03-25
 */
@Configuration
public class SessionConfig implements WebMvcConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(SessionConfig.class);
    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            //排除拦截
            .excludePathPatterns("/user/login")
            .excludePathPatterns("/user/admin/login")
            //拦截路径
            .addPathPatterns("/**");
        registry.addInterceptor(new LogInterceptor())
            .addPathPatterns("/**");
    }

    public class LogInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            logger.info("request to {}", request.getRequestURL());
            return true;
        }
    }

    public class LoginInterceptor implements HandlerInterceptor {
        @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            var session = request.getSession();
            Object userId = session.getAttribute(session.getId());
            logger.info("user {} assessed: {}.", userId, request.getRequestURL());
            if (userId != null) {
                return true;
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
