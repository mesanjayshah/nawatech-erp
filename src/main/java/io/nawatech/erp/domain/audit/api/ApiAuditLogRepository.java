package io.nawatech.erp.domain.audit.api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiAuditLogRepository extends JpaRepository<ApiAuditLog, Long>, ApiAuditLogCustomRepository {
}
