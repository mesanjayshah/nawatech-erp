package io.nawatech.erp.domain.audit.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiAuditLogService {

    private final ApiAuditLogRepository repository;
    private final ApplicationEventPublisher publisher;

    public void publishApiAuditLog(ApiAuditLog log) {
        publisher.publishEvent(new ApiAuditLogEvent(this, List.of(log)));
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAuditLog(ApiAuditLog log) {
        repository.save(log);
    }
}


