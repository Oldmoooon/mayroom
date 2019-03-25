package name.guyue.backend.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hujia
 * @date 2019-03-22
 */
@Aspect
@Component
public class DemoAspect {
    private static Logger logger = LoggerFactory.getLogger(DemoAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    public void demoPointCut() { }

    @Before("demoPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("REQUEST: {}", joinPoint.getArgs());
        System.out.println("REQUEST: " + Arrays.toString(joinPoint.getArgs()));
    }
}
