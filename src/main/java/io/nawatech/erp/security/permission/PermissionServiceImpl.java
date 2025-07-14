package io.nawatech.erp.security.permission;

import io.nawatech.erp.admin.Admin;
import io.nawatech.erp.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final AdminRepository adminRepository;

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, String permission) {
        if (auth == null || !auth.isAuthenticated()) return false;

        String username = extractUsername(auth);
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null || admin.getRoles() == null) return false;

        return admin.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(p -> p.getName().equalsIgnoreCase(permission));
    }

    @Override
    public boolean hasPermission(Authentication auth, String targetType, String permission) {
        // In case you want to allow finer checks later by targetType, extend here
        return hasPermission(auth, null, permission);
    }

    private String extractUsername(Authentication auth) {
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return String.valueOf(principal);
    }
}