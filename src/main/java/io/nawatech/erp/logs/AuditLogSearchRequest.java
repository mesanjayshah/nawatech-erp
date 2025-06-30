package io.nawatech.erp.logs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogSearchRequest {

    private String principal;
    private AuditEventType eventType;
    private String ipAddress;
    private LocalDateTime from;
    private LocalDateTime to;
    private int page = 0;
    private int size = 20;


}

