package io.nawatech.erp.master.repository;

import io.nawatech.erp.master.entity.ApiAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiAuditLogRepository extends JpaRepository<ApiAuditLog, Long> {
}
