package io.nawatech.erp.audit.api;

import org.springframework.data.domain.Page;

public interface ApiAuditLogCustomRepository {
    Page<ApiAuditLog> search(ApiAuditLogSearchRequest request);
}
