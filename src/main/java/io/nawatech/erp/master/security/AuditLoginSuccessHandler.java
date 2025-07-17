package io.nawatech.erp.master.security;

import io.nawatech.erp.master.api.ApiAuditEventType;
import io.nawatech.erp.master.entity.ApiAuditLog;
import io.nawatech.erp.master.service.ApiAuditLogService;
import io.nawatech.erp.master.multitenant.TenantContext;
import io.nawatech.erp.master.multitenant.TenantProvisioningService;
import io.nawatech.erp.master.service.UserTenantMappingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AuditLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ApplicationContext context;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String email = authentication.getName();
        var tenantService = context.getBean(UserTenantMappingService.class);
        var provisioner = context.getBean(TenantProvisioningService.class);

        // Auto-provision if needed
        try {
            String tenantId = tenantService.findTenantForUser(email);
            TenantContext.setTenant(tenantId);
        } catch (Exception e) {
            provisioner.provisionTenant(email);
            String tenantId = tenantService.findTenantForUser(email);
            TenantContext.setTenant(tenantId);
        }

        ApiAuditLogService auditLogService = context.getBean(ApiAuditLogService.class);

        ApiAuditLog log = new ApiAuditLog();
        log.setPrincipal(authentication.getName());
        log.setEventType(ApiAuditEventType.LOGIN_SUCCESS);
        log.setHttpMethod(request.getMethod());
        log.setRequestUri(request.getRequestURI());
        log.setIpAddress(request.getRemoteAddr());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setRequestTime(LocalDateTime.now());
        log.setStatusCode(HttpServletResponse.SC_OK);
        log.setResponseTimeMs(0);

        auditLogService.saveAuditLog(log);

        response.sendRedirect("/");
    }
}