package io.nawatech.erp.master.multitenant;

import io.nawatech.erp.master.service.UserTenantMappingService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class TenantResolverFilter extends OncePerRequestFilter {

    private final UserTenantMappingService userTenantMappingService;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/login", "/register", "/index", "/2fa", "/api/rate-limited",
            "/verifyEmail", "/password-reset", "/resend-verification-token",
            "/forgot-password", "/forgot-password-token", "/token/verify",
            "/assets", "/vendors", "/favicon.ico", "/css", "/js", "/images", "/webjars",
            "/oauth2"
    );

    private static final List<String> STATIC_FILE_EXTENSIONS = List.of(
            ".css", ".js", ".png", ".jpg", ".jpeg", ".gif",
            ".svg", ".ico", ".woff", ".woff2", ".ttf", ".eot", ".map"
    );

    public TenantResolverFilter(UserTenantMappingService userTenantMappingService) {
        this.userTenantMappingService = userTenantMappingService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        log.debug("TenantResolverFilter inspecting path: {}", path);

        if (isPublicPath(path)) {
            log.debug("TenantResolverFilter: Skipping tenant resolution for public/static path: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            try {
                String tenantId = userTenantMappingService.findTenantForUser(email);
                TenantContext.setTenant(tenantId);
                log.debug("TenantResolverFilter: Resolved tenant {} for user {}", tenantId, email);
            } catch (Exception e) {
                log.warn("TenantResolverFilter: No tenant mapped for user {}", email);
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }


    private boolean isPublicPath(String path) {
        // Matches common public paths
        boolean isPublic = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

        // Matches common static folders
        boolean isStaticFolder = path.startsWith("/assets")
                || path.startsWith("/static")
                || path.startsWith("/resources")
                || path.startsWith("/webjars")
                || path.startsWith("/vendors")
                || path.startsWith("/css")
                || path.startsWith("/js")
                || path.startsWith("/images")
                || path.startsWith("/fonts");

        // Matches static file extensions
        boolean isStaticExtension = STATIC_FILE_EXTENSIONS.stream().anyMatch(path::endsWith);

        return isPublic || isStaticFolder || isStaticExtension;
    }
}
