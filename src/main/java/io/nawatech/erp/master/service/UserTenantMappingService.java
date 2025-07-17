package io.nawatech.erp.master.service;

import io.nawatech.erp.master.repository.UserTenantMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTenantMappingService {

    @Autowired
    private UserTenantMappingRepository userTenantMappingRepository;

    public String findTenantForUser(String email) {
        String tenant = userTenantMappingRepository.findTenantIdByEmail(email)
                .orElse(null);

        System.out.println("LOOKUP: " + tenant);

        return tenant;
    }
}
