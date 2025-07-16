package io.nawatech.erp.audit.entitychange;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityChangeLogRepository extends JpaRepository<EntityChangeDetailInfo, Long> {}
