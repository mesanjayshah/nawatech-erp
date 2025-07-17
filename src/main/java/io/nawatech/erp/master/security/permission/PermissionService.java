package io.nawatech.erp.master.security.permission;

import org.springframework.security.core.Authentication;

public interface PermissionService {
    boolean hasPermission(Authentication auth, Object targetDomainObject, String permission);
    boolean hasPermission(Authentication auth, String targetType, String permission);
}