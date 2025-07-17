package io.nawatech.erp.domain.audit.api;

import org.springframework.data.domain.Page;

public interface ApiAuditLogCustomRepository {
    Page<ApiAuditLog> search(ApiAuditLogSearchRequest request);
}
