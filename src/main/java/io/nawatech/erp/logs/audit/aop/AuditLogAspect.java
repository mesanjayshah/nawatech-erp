package io.nawatech.erp.logs.audit.aop;

import io.nawatech.erp.logs.audit.AuditLogFlusher;
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
public class AuditLogAspect {

    private final ApplicationEventPublisher publisher;

    @PersistenceContext
    private EntityManager entityManager;

    @Around("@annotation(io.nawatech.erp.logs.audit.aop.TrackBusinessAudit)")
    public Object auditAround(ProceedingJoinPoint joinPoint) throws Throwable {
        AuditLogFlusher.register(publisher);
        Object result = joinPoint.proceed();
        entityManager.flush();
        return result;
    }
}
