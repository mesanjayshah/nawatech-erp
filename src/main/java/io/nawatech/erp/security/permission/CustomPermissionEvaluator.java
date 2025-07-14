package io.nawatech.erp.security.permission;

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

        log.debug("Authentication: {}", authentication);
        log.debug("Checking permission {} for target {}", permission, targetDomainObject);

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        // ✅ Skip permission checks for static or framework-related requests
        if (targetDomainObject == null || permission == null) {
            return true;
        }

        if (!(permission instanceof String)) {
            return false;
        }

        return permissionService.hasPermission(authentication, targetDomainObject, permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        // Allow nulls to skip unnecessary checks
        if (targetId == null || targetType == null || permission == null) {
            return true;
        }

        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }

        return permissionService.hasPermission(auth, targetType, String.valueOf(permission));
    }
}