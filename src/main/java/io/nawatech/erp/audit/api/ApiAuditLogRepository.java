package io.nawatech.erp.audit.api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiAuditLogRepository extends JpaRepository<ApiAuditLog, Long>, ApiAuditLogCustomRepository {
}
