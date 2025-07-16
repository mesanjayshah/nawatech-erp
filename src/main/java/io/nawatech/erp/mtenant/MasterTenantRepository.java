package io.nawatech.erp.mtenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterTenantRepository extends JpaRepository<MasterTenant, String> {

     Optional<MasterTenant> findByTenantId(String tenantId);
}
