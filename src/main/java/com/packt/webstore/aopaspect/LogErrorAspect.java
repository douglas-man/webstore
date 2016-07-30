package com.packt.webstore.aopaspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Arrays;

/**
 * Created by dman on 7/11/16.
 */
@Aspect
public class LogErrorAspect {
    private static final Logger logger =
            LoggerFactory.getLogger(LogErrorAspect.class);

    @AfterThrowing(value = "execution(* *.*(..))", throwing = "e")
    public void afterAnyMethod(JoinPoint call, Throwable e) {
        Signature signature = call.getSignature();
        String methodName = signature.getName();
        String arguments = Arrays.toString(call.getArgs());

        logger.error("A Caught Exception in: " + call.getTarget().getClass().getName() + "."
        + methodName + " with arguments "
        + arguments + "\nthe exception is: "
        + e.getMessage(), e);
    }
}
