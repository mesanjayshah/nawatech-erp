package io.nawatech.erp.mtenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class LoginBasedTenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getTenant();
        return (tenantId != null) ? tenantId : "default";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}

