package com.packt.webstore.aopaspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Object;
/**
 * Created by dman on 7/11/16.
 */
@Aspect
public class LogInfoAspect {
    private static final Logger logger =
            LoggerFactory.getLogger(LogInfoAspect.class);

    @Pointcut("execution(** com.packt.webstore.service.OrderService.processOrder(..))")
    public void ServicePointcut() {

    }

    @Before("ServicePointcut()")
    public void logServiceCalls(JoinPoint call/*, String productId, long count*/) /*throws Throwable*/ {
        Object[] arguments = call.getArgs();
        Object target = call.getTarget();

        logger.info("{} Service is Called...", target.getClass().getName());
        logger.info("{} is executed, value {}", call.getSignature().getName(), arguments[0]);
    }
}
