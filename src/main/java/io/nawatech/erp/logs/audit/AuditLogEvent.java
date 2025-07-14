package io.nawatech.erp.logs.audit;

import org.springframework.context.ApplicationEvent;
import java.util.List;
import java.util.Set;

public class AuditLogEvent extends ApplicationEvent {
    private final Set<AuditLogDetailInfo> logs;

    public AuditLogEvent(Object source, Set<AuditLogDetailInfo> logs) {
        super(source);
        this.logs = logs;
    }

    public Set<AuditLogDetailInfo> getLogs() {
        return logs;
    }
}