package io.nawatech.erp.logs.audit;

import org.springframework.context.ApplicationEvent;
import java.util.List;

public class AuditLogEvent extends ApplicationEvent {
    private final List<AuditLogDetailInfo> logs;

    public AuditLogEvent(Object source, List<AuditLogDetailInfo> logs) {
        super(source);
        this.logs = logs;
    }

    public List<AuditLogDetailInfo> getLogs() {
        return logs;
    }
}