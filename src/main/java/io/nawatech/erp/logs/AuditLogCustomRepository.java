package io.nawatech.erp.logs;

import org.springframework.data.domain.Page;

public interface AuditLogCustomRepository {
    Page<AuditLog> search(AuditLogSearchRequest request);
}