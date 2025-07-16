package io.nawatech.erp.security;

import io.nawatech.erp.mtenant.TenantContext;
import io.nawatech.erp.mtenant.TenantProvisioningService;
import io.nawatech.erp.mtenant.UserTenantMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserTenantMappingService userTenantMappingService;

    @Autowired
    private TenantProvisioningService tenantProvisioningService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User user = super.loadUser(request);
        String email = user.getAttribute("email");

        // Try to find an existing tenant
        String tenant = userTenantMappingService.findTenantForUser(email);

        if (tenant == null) {
            // Provision a new tenant — will generate tenantId and store mapping
            tenantProvisioningService.provisionTenant(email);

            // Re-query the mapping now that it's created
            tenant = userTenantMappingService.findTenantForUser(email);
        }

        TenantContext.setTenant(tenant);
        return user;
    }
}
