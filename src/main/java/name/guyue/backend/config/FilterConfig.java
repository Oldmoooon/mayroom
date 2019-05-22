package name.guyue.backend.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hujia
 * @date 2019-05-22
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HttpRequestFilter> registrationBean() {
        FilterRegistrationBean<HttpRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpRequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("HttpRequestFilter");
        registration.setOrder(1);
        return registration;
    }
}
