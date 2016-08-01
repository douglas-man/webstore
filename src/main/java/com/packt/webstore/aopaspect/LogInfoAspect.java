package com.packt.webstore.aopaspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Object;
import java.util.Arrays;

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
        String arguments = Arrays.toString(call.getArgs());
        Object target = call.getTarget();

        logger.info("{} Service is Called...\n " +
                " {} is executed, with {}", target.getClass().getName(), call.getSignature().getName());
//        logger.info("\n{} is executed, value {}", call.getSignature().getName(), arguments[0]);
    }
}
