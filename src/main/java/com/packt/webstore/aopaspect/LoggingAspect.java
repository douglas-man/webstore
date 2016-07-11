package com.packt.webstore.aopaspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dman on 7/11/16.
 */
@Aspect
public class LoggingAspect {
    private static final Logger logger =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.packt.webstore.service.OrderService.processOrder(..))")
    public void ServicePointcut() {

    }

    @Before("ServicePointcut()")
    public void logServiceCalls() {
        logger.info("processOrder() is executed, value {}", "dman");
    }
}
