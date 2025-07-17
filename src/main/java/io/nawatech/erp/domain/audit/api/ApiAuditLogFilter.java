package io.nawatech.erp.domain.audit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nawatech.erp.master.admin.Admin;
import io.nawatech.erp.master.admin.AdminRepository;
import io.nawatech.erp.master.security.permission.PermissionAuditContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiAuditLogFilter extends OncePerRequestFilter {

    private final ApiAuditLogService auditLogService;
    private final AdminRepository adminRepository;
    private final PermissionAuditContext auditContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.matches(".*\\.(css|js|ico|png|jpg|svg|woff2?)$")
                || uri.startsWith("/erp/assets/")
                || uri.startsWith("/erp/vendors/")
                || uri.equals("/favicon.ico")) {
            chain.doFilter(request, response);
            return;
        }

        long start = System.currentTimeMillis();
        String principal = "anonymous";
        Admin user = null;

        if (request.getUserPrincipal() != null) {
            principal = request.getUserPrincipal().getName();

            // Try resolving the Admin entity using email (for both local + Google logins)
            user = adminRepository.findByEmail(principal).orElse(null);

            // Optional fallback for Google sub-based principal (not recommended unless used)
            if (user == null && principal.matches("\\d+")) {
                user = adminRepository.findByProviderId(principal).orElse(null);
            }
        }

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        Map<String, Object> details = new LinkedHashMap<>();
        details.put("permission", auditContext.getPermissionChecked());
        details.put("granted", auditContext.isAccessGranted());
        details.put("reason", auditContext.getPermissionReason());
        details.put("idp", user != null ? user.getProvider() : "anonymous");

        ApiAuditLog log = new ApiAuditLog();
        log.setEventType(ApiAuditEventType.API_CALL);
        log.setPrincipal(principal);
        log.setIdpName(user != null ? user.getProvider() : "anonymous");
        log.setHttpMethod(request.getMethod());
        log.setRequestUri(request.getRequestURI());
        log.setIpAddress(request.getRemoteAddr());
        log.setRequestTime(LocalDateTime.now());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setStatusCode(response.getStatus());
        log.setResponseTimeMs(duration);
        log.setDetails(new ObjectMapper().writeValueAsString(details));
        log.setUser(user);
        log.setAccessGranted(auditContext.isAccessGranted());
        log.setPermissionChecked(auditContext.getPermissionChecked());
        log.setPermissionReason(auditContext.getPermissionReason());

        auditLogService.saveAuditLog(log);
    }
}
