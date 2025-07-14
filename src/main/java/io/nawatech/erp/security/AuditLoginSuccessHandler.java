package io.nawatech.erp.security;

import io.nawatech.erp.logs.AuditEventType;
import io.nawatech.erp.logs.AuditLog;
import io.nawatech.erp.logs.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuditLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        AuditLog log = new AuditLog();
        log.setPrincipal(authentication.getName());
        log.setEventType(AuditEventType.LOGIN_SUCCESS);
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