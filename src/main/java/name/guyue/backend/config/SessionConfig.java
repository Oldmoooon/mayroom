package name.guyue.backend.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class SessionConfig implements WebMvcConfigurer {
    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            //排除拦截
            .excludePathPatterns("/login")
            .excludePathPatterns("/logout")

            //拦截路径
            .addPathPatterns("/**");
    }

    public class LoginInterceptor implements HandlerInterceptor {
        @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            var session = request.getSession();
            if (session.getAttribute(session.getId()) != null) {
                return true;
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
