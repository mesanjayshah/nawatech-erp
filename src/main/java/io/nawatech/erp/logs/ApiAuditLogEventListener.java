package io.nawatech.erp.logs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiAuditLogEventListener {

    private final AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleApiAuditLogEvent(AuditLogEvent event) {
        var logs = event.logs();
        if (!logs.isEmpty()) {
            log.info("✅ Persisting {} API audit logs in AFTER_COMMIT", logs.size());
            auditLogRepository.saveAll(logs);
        }
    }
}