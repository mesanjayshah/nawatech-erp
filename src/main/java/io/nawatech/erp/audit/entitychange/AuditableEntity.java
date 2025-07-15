package io.nawatech.erp.audit.entitychange;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
@EntityListeners(EntityChangeEventListener.class)
public abstract class AuditableEntity {

    @Transient
    private Map<String, Object> originalState = new HashMap<>();

    @PostLoad
    public void captureOriginalState() {
        if (originalState == null || originalState.isEmpty()) {
            originalState = EntityChangeUtil.captureOriginalState(this);
        }
    }

    public Map<String, Object> getOriginalState() {
        return originalState != null ? originalState : Map.of();
    }
}
