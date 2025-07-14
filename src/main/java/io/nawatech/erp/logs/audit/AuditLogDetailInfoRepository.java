package io.nawatech.erp.logs.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogDetailInfoRepository extends JpaRepository<AuditLogDetailInfo, Long> {

    // Optional: Add custom queries if needed, e.g.:
    // List<AuditLogDetailInfo> findByEntityName(String entityName);
}
