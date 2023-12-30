package com.dev.company.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class TrackExecutionTimeAspect {

    @Pointcut("within(@com.dev.company.aspect.TrackExecutionTime *)")
    public void interfacePointcut() {
    }

    @Pointcut("within(com.dev.company..*)" +
            " || within(com.dev.company.controller..*)" +
            " || within(com.dev.company.service..*)" +
            " || within(com.dev.company.rest..*)" +
            " || within(com.dev.company.rest.converter..*)" +
            " || within(com.dev.company.repository..*)")
    public void applicationPackagePointcut() {
    }

    @Around("interfacePointcut() || applicationPackagePointcut()")
//    @Around("@annotation(TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.debug("Time taken for execution: {}.{}() is {} ms",
                getClassName(joinPoint.getSignature().getDeclaringTypeName()),
                joinPoint.getSignature().getName(),
                (endTime-startTime));
        return object;
    }

    private String getClassName(String fullPath) {
        String[] arr = fullPath.split("\\.");
        int lastIndex = arr.length - 1;
        return arr[lastIndex];
    }
}
