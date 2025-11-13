package smartLegalApi.infrastructure.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect para monitoramento de performance
 * Loga métodos que levam muito tempo para executar
 */
@Slf4j
@Aspect
@Component
public class PerformanceMonitorAspect {

    // Threshold em milissegundos para alertar sobre métodos lentos
    private static final long SLOW_METHOD_THRESHOLD = 1000; // 1 segundo

    /**
     * Pointcut para monitorar performance em toda a aplicação
     */
    @Pointcut("within(smartLegalApi..*) && !within(smartLegalApi.infrastructure.logging..*)")
    public void applicationLayer() {}

    /**
     * Monitora tempo de execução de métodos
     */
    @Around("applicationLayer()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        Object result = joinPoint.proceed();
        
        long executionTime = System.currentTimeMillis() - startTime;
        
        // Alerta se o método for muito lento
        if (executionTime > SLOW_METHOD_THRESHOLD) {
            log.warn("⚠ SLOW METHOD: {}.{}() levou {}ms para executar",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                executionTime
            );
        } else if (executionTime > 500) {
            // Log de debug para métodos moderadamente lentos
            log.debug("⏱ {}.{}() executado em {}ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                executionTime
            );
        }
        
        return result;
    }
}

