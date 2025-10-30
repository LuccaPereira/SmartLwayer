package smartLegalApi.infrastructure.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect para logging automático usando AOP
 * Loga entrada e saída de métodos em controllers, services e repositories
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * Pointcut para todos os controllers
     */
    @Pointcut("within(smartLegalApi.presentation.controller..*)")
    public void controllerLayer() {}

    /**
     * Pointcut para todos os use cases (application layer)
     */
    @Pointcut("within(smartLegalApi.application..usecase..*)")
    public void useCaseLayer() {}

    /**
     * Pointcut para todos os repositories
     */
    @Pointcut("within(smartLegalApi.infrastructure.persistence..*)")
    public void repositoryLayer() {}

    /**
     * Pointcut para todos os domain services
     */
    @Pointcut("within(smartLegalApi.domain..service..*)")
    public void domainServiceLayer() {}

    /**
     * Loga entrada em métodos de controller
     */
    @Before("controllerLayer()")
    public void logBeforeController(JoinPoint joinPoint) {
        log.info("→ REST API: {}.{}() com argumentos: {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            Arrays.toString(joinPoint.getArgs())
        );
    }

    /**
     * Loga saída de métodos de controller
     */
    @AfterReturning(pointcut = "controllerLayer()", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        log.info("← REST API: {}.{}() retornou: {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            result != null ? result.getClass().getSimpleName() : "void"
        );
    }

    /**
     * Loga exceções em controllers
     */
    @AfterThrowing(pointcut = "controllerLayer()", throwing = "exception")
    public void logAfterThrowingController(JoinPoint joinPoint, Throwable exception) {
        log.error("✗ REST API: {}.{}() lançou exceção: {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            exception.getMessage()
        );
    }

    /**
     * Loga entrada em use cases
     */
    @Before("useCaseLayer()")
    public void logBeforeUseCase(JoinPoint joinPoint) {
        log.debug("→ UseCase: {}.{}()",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName()
        );
    }

    /**
     * Loga saída de use cases
     */
    @AfterReturning(pointcut = "useCaseLayer()")
    public void logAfterUseCase(JoinPoint joinPoint) {
        log.debug("← UseCase: {}.{}() concluído",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName()
        );
    }

    /**
     * Loga operações de repositório
     */
    @Around("repositoryLayer()")
    public Object logAroundRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        log.debug("→ Repository: {}.{}()",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName()
        );
        
        Object result = joinPoint.proceed();
        
        long executionTime = System.currentTimeMillis() - startTime;
        
        log.debug("← Repository: {}.{}() executado em {}ms",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            executionTime
        );
        
        return result;
    }

    /**
     * Loga operações em domain services
     */
    @Before("domainServiceLayer()")
    public void logBeforeDomainService(JoinPoint joinPoint) {
        log.debug("→ DomainService: {}.{}()",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName()
        );
    }
}

