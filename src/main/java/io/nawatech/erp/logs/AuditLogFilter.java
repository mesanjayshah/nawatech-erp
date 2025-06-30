package io.nawatech.erp.logs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuditLogFilter extends OncePerRequestFilter {

    @Autowired
    private AuditLogService auditLogService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();
        String principal = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous";

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        AuditLog log = new AuditLog();
        log.setEventType(AuditEventType.API_CALL);
        log.setPrincipal(principal);
        log.setHttpMethod(request.getMethod());
        log.setRequestUri(request.getRequestURI());
        log.setIpAddress(request.getRemoteAddr());
        log.setRequestTime(LocalDateTime.now());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setStatusCode(response.getStatus());
        log.setResponseTimeMs(duration);

        auditLogService.saveAuditLog(log);
    }
}
