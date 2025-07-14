package io.nawatech.erp.logs.audit;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.context.ApplicationEventPublisher;

public class AuditLogFlusher {

    public static void register(ApplicationEventPublisher publisher) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    var logs = AuditLogContext.getAndClear();
                    System.out.println("📌 Flushing logs via event: " + logs.size());
                    if (!logs.isEmpty()) {
                        publisher.publishEvent(new AuditLogEvent(this, logs));
                    }
                }
            });
        }
    }
}