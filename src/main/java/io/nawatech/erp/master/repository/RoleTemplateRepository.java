package io.nawatech.erp.master.repository;

import io.nawatech.erp.master.entity.RoleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleTemplateRepository extends JpaRepository<RoleTemplate, Long> {

    Optional<RoleTemplate> findByName(String name);

}
