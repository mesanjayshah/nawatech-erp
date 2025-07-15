package io.nawatech.erp.audit.entitychange;

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
public class EntityChangeEventListener {

    private final EntityChangeLogRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(EntityChangeEvent event) {
        var logs = event.getLogs();
        log.info("✅ Persisting {} entity change logs AFTER_COMMIT", logs.size());
        repository.saveAll(logs);
    }
}
