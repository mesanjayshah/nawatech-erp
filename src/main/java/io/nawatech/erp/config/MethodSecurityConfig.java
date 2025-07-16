package io.nawatech.erp.config;

import io.nawatech.erp.mtenant.TenantContext;
import io.nawatech.erp.security.permission.CustomPermissionEvaluator;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import io.nawatech.erp.security.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class MethodSecurityConfig {

    private final PermissionService permissionService;

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        var handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator());
        return handler;
    }

    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new CustomPermissionEvaluator(permissionService);
    }
}
