package io.nawatech.erp.master.api;

import io.nawatech.erp.master.entity.ApiAuditLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class ApiAuditLogEvent extends ApplicationEvent {
    private final List<ApiAuditLog> logs;

    public ApiAuditLogEvent(Object source, List<ApiAuditLog> logs) {
        super(source);
        this.logs = logs;
    }

}
