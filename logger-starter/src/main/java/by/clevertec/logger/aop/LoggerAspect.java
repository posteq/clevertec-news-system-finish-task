package by.clevertec.logger.aop;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@AllArgsConstructor
@Component
public class LoggerAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void withinController() {
    }

    @Pointcut("@annotation(by.clevertec.logger.annotation.LogIt)")
    public void loggableMethods() {
    }

    @Before("withinController() || loggableMethods()")
    public void beforeLayer(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        log.info("{} with args {}", joinPoint.toString(), Arrays.toString(args));
    }

    @AfterThrowing(value = "withinController()", throwing = "exception")
    public void afterControllerThrowingException(JoinPoint joinPoint, Exception exception) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        log.info("{} with exception {}", methodName, exception.toString());
    }

    @AfterReturning(value = "withinController() || loggableMethods()", returning = "returnValue")
    public void afterController(JoinPoint joinPoint, Object returnValue) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String returnedValueAsString = returnValue == null ? "" : returnValue.toString();
        log.info("{} with response {}", methodName, returnedValueAsString);
    }
}

