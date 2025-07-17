package io.nawatech.erp.master.repository;

import io.nawatech.erp.master.entity.UserTenantMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserTenantMappingRepository extends CrudRepository<UserTenantMapping, Long> {

    @Query("SELECT u.tenantId FROM UserTenantMapping u WHERE u.email = :email")
    Optional<String> findTenantIdByEmail(String email);
}
