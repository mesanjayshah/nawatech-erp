package io.nawatech.erp.master.security.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final PermissionService permissionService;

    public CustomPermissionEvaluator(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || !authentication.isAuthenticated()) return false;
        if (!(permission instanceof String permissionStr) || permissionStr.isBlank()) return false;

        log.debug("Checking permission '{}' for '{}'", permissionStr, targetDomainObject);
        return permissionService.hasPermission(authentication, targetDomainObject, permissionStr);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || !authentication.isAuthenticated()) return false;
        if (!(permission instanceof String permissionStr) || permissionStr.isBlank()) return false;

        return permissionService.hasPermission(authentication, targetType, permissionStr);
    }
}