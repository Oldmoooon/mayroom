package name.guyue.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author hujia
 * @date 2019-03-25
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
