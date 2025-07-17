package io.nawatech.erp.domain.audit.entitychange;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityChangeLogRepository extends JpaRepository<EntityChangeDetailInfo, Long> {}
