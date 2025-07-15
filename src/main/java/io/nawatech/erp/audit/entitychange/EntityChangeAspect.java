package io.nawatech.erp.audit.entitychange;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class EntityChangeAspect {

    private final ApplicationEventPublisher publisher;

    @PersistenceContext
    private EntityManager entityManager;

    @Around("@annotation(io.nawatech.erp.audit.entitychange.TrackEntityChange)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        EntityChangeFlusher.register(publisher);
        Object result = joinPoint.proceed();
        entityManager.flush();
        return result;
    }
}
