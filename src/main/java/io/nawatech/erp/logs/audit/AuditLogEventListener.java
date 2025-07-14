package io.nawatech.erp.logs.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
public class AuditLogEventListener {

    private final AuditLogDetailInfoRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAuditLogEvent(AuditLogEvent event) {


        System.out.println("🔍 Is transaction active? " + TransactionSynchronizationManager.isActualTransactionActive());
        var logs = event.getLogs();
        if (!logs.isEmpty()) {
            System.out.println("✅ Persisting " + logs.size() + " logs in AFTER_COMMIT");
            repository.saveAll(logs);
        }
    }
}