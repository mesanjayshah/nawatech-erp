package io.nawatech.erp.mtenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class TenantResolverFilter extends OncePerRequestFilter {

    private final UserTenantMappingService userTenantMappingService;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/login", "/register", "/index", "/2fa", "/api/rate-limited",
            "/verifyEmail", "/password-reset", "/resend-verification-token",
            "/forgot-password", "/forgot-password-token", "/token/verify",
            "/assets", "/vendors", "/favicon.ico", "/css", "/js", "/images", "/webjars",
            "/oauth2"
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

        // Skip tenant resolution for static + public endpoints
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            String tenantId = userTenantMappingService.findTenantForUser(email);
            TenantContext.setTenant(tenantId);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}
