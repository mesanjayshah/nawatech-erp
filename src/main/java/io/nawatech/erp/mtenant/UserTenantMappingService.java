package io.nawatech.erp.mtenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTenantMappingService {

    @Autowired
    private UserTenantMappingRepository userTenantMappingRepository;

    public String findTenantForUser(String email) {
        return userTenantMappingRepository.findTenantIdByEmail(email)
                .orElse(null);
    }
}
