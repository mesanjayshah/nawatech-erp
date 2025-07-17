package io.nawatech.erp.domain.tenant.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
