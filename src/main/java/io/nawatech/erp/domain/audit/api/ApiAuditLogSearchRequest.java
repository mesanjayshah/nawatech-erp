package io.nawatech.erp.domain.audit.api;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiAuditLogSearchRequest {

    private String principal;
    private ApiAuditEventType eventType;
    private String ipAddress;
    private LocalDateTime from;
    private LocalDateTime to;
    private int page = 0;
    private int size = 20;
}
