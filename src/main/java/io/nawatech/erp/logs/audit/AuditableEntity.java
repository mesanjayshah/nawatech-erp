package io.nawatech.erp.logs.audit;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    @Transient
    private Map<String, Object> originalState = new HashMap<>();

    @PostLoad
    public void captureOriginalStateIfMissing() {
        if (originalState == null || originalState.isEmpty()) {
            originalState = AuditUtil.captureOriginalState(this);
        }
    }

    public Map<String, Object> getOriginalState() {
        return originalState != null ? originalState : Map.of();
    }
}
