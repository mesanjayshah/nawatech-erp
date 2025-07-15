package io.nawatech.erp.logs;

import java.util.List;

public record AuditLogEvent(List<AuditLog> logs) {}