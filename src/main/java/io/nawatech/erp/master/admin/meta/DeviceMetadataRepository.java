package io.nawatech.erp.master.admin.meta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    List<DeviceMetadata> findByAdminId(Long adminId);

}
