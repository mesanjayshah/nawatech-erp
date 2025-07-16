package io.nawatech.erp.audit.entitychange;

import org.springframework.context.ApplicationEvent;

import java.util.Set;

public class EntityChangeEvent extends ApplicationEvent {
    private final Set<EntityChangeDetailInfo> logs;

    public EntityChangeEvent(Object source, Set<EntityChangeDetailInfo> logs) {
        super(source);
        this.logs = logs;
    }

    public Set<EntityChangeDetailInfo> getLogs() {
        return logs;
    }
}
