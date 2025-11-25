package com.example.sarabom.global.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Around("execution(* com.example.sarabom.domain..application..*(..))" +
            " || execution(* com.example.sarabom.domain..infrastructure..*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();

        String argsString = formatArgs(args);
        log.info("[{}.{}] args: {}", className, methodName, argsString);

        return joinPoint.proceed();
    }

    private String formatArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }

        return Arrays.stream(args)
                .map(this::toJson)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        if (obj instanceof String || obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            // If JSON serialization fails (circular reference, etc.), fallback to toString()
            return obj.toString();
        }
    }
}
