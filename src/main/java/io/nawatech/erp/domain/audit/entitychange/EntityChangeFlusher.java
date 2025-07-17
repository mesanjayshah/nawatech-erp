package io.nawatech.erp.domain.audit.entitychange;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class EntityChangeFlusher {

    public static void register(ApplicationEventPublisher publisher) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    var logs = EntityChangeContext.getAndClear();
                    System.out.println("📌 Flushing entity change logs: " + logs.size());
                    if (!logs.isEmpty()) {
                        publisher.publishEvent(new EntityChangeEvent(this, logs));
                    }
                }
            });
        }
    }
}
