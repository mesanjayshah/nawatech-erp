package io.nawatech.erp.master.security.permission;

import io.nawatech.erp.master.admin.Admin;
import io.nawatech.erp.master.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final AdminRepository adminRepository;
    private final PermissionAuditContext auditContext;

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, String permission) {
        if (auth == null || !auth.isAuthenticated()) return false;

        String username = extractUsername(auth);
        Optional<Admin> adminOpt = adminRepository.findByEmail(username);
        if (adminOpt.isEmpty() || adminOpt.get().getRoles() == null) {
            return false;
        }

        Admin admin = adminOpt.get();
        auditContext.setPermissionChecked(permission);

        Set<String> granted = admin.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(p -> p.getName().toLowerCase()) // normalize to lowercase
                .collect(Collectors.toSet());


        log.info("User '{}' permissions = {}", username, granted);
        boolean allowed = granted.contains(permission.toLowerCase());

        auditContext.setAccessGranted(allowed);
        auditContext.setPermissionReason(allowed
                ? "Permission found in role(s)"
                : "Permission missing in all assigned roles");

        if (!allowed) {
            log.warn("Access denied. '{}' missing permission '{}'", username, permission);
        }

        return allowed;
    }

    @Override
    public boolean hasPermission(Authentication auth, String targetType, String permission) {
        // In case you want to allow finer checks later by targetType, extend here
        return hasPermission(auth, null, permission);
    }

    private String extractUsername(Authentication auth) {
        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            // Local login - return username (email)
            return userDetails.getUsername();
        }

        if (principal instanceof DefaultOidcUser oidcUser) {
            // OIDC login - extract email from claims/attributes
            Object emailAttr = oidcUser.getAttributes().get("email");
            if (emailAttr instanceof String email) {
                return email;
            }
        }

        // Fallback to principal's toString() if above not matched
        return String.valueOf(principal);
    }

}