package io.nawatech.erp.mtenant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserTenantMappingRepository extends CrudRepository<UserTenantMapping, Long> {

    @Query("SELECT u.tenantId FROM UserTenantMapping u WHERE u.email = :email")
    Optional<String> findTenantIdByEmail(String email);
}
