package io.nawatech.erp.audit.entitychange;

import org.springframework.context.ApplicationEvent;

import java.util.Set;

public class EntityChangeEvent extends ApplicationEvent {
    private final Set<EntityChangeLog> logs;

    public EntityChangeEvent(Object source, Set<EntityChangeLog> logs) {
        super(source);
        this.logs = logs;
    }

    public Set<EntityChangeLog> getLogs() {
        return logs;
    }
}
