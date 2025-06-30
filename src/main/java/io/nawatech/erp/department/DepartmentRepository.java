package io.nawatech.erp.department;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("update Department d set d.isDeleted = false where d.id=:id")
    @Transactional
    @Modifying
    boolean deleteDepartmentById(Long id);
}
