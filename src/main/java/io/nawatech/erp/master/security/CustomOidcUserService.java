package io.nawatech.erp.master.security;

import io.nawatech.erp.domain.audit.api.ApiAuditEventType;
import io.nawatech.erp.domain.audit.api.ApiAuditLog;
import io.nawatech.erp.domain.audit.api.ApiAuditLogService;
import io.nawatech.erp.master.multitenant.TenantContext;
import io.nawatech.erp.master.multitenant.TenantProvisioningService;
import io.nawatech.erp.master.service.UserTenantMappingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import io.nawatech.erp.master.admin.Admin;
import io.nawatech.erp.master.admin.AdminRepository;
import io.nawatech.erp.master.admin.AdminService;
import io.nawatech.erp.domain.tenant.role.Role;
import io.nawatech.erp.domain.tenant.role.RoleRepository;
import io.nawatech.erp.utils.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private AdminService userService;
    @Autowired
    private AdminRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ApiAuditLogService auditLogService;

    @Autowired
    private UserTenantMappingService userTenantMappingService;

    @Autowired
    private TenantProvisioningService tenantProvisioningService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getEmail();

        // ✅ Tenant provision logic goes HERE:
        String tenant = userTenantMappingService.findTenantForUser(email);
        if (tenant == null) {
            tenantProvisioningService.provisionTenant(email);
            tenant = userTenantMappingService.findTenantForUser(email);
        }
        TenantContext.setTenant(tenant);

        String sub = oidcUser.getSubject();
        String name = oidcUser.getFullName();
        String givenName = oidcUser.getGivenName();
        String familyName = oidcUser.getFamilyName();
        String pictureUrl = oidcUser.getPicture();
        Boolean emailVerified = oidcUser.getEmailVerified();
        String locale = oidcUser.getLocale();
        String accessToken = userRequest.getAccessToken().getTokenValue();

        Optional<Admin> userOpt = userService.findByEmail(email);
        Set<Role> defaultRole = Collections.singleton(
                roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new RuntimeException("Default role not found"))
        );

        Admin user;
        if (userOpt.isPresent()) {
            user = userOpt.get();
            user.setEmail(email);
            user.setName(name);
            user.setGivenName(givenName);
            user.setFamilyName(familyName);
            user.setPictureUrl(pictureUrl);
            user.setEmailVerified(emailVerified);
            user.setLocale(locale);
            user.setAccessToken(accessToken);
            user.setLoginCount(user.getLoginCount() == null ? 1 : user.getLoginCount() + 1);
            user.setLastLoginAt(LocalDateTime.now());
            user.setLastLoginIp(CommonUtils.getClientIp(request));
        } else {
            user = Admin.builder()
                    .provider("GOOGLE")
                    .providerId(sub)
                    .email(email)
                    .name(name)
                    .givenName(givenName)
                    .familyName(familyName)
                    .pictureUrl(pictureUrl)
                    .emailVerified(emailVerified)
                    .locale(locale)
                    .accessToken(accessToken)
                    .roles(defaultRole)
                    .loginCount(1L)
                    .lastLoginAt(LocalDateTime.now())
                    .lastLoginIp(CommonUtils.getClientIp(request))
                    .build();
        }

        // Ensure roles are set
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(defaultRole);
        }

        userRepository.save(user);

        // ✅ Log login event
        ApiAuditLog log = new ApiAuditLog();
        log.setEventType(ApiAuditEventType.LOGIN_SUCCESS);
        log.setPrincipal(email);
        log.setHttpMethod(request.getMethod());
        log.setRequestUri(request.getRequestURI());
        log.setIpAddress(CommonUtils.getClientIp(request));
        log.setRequestTime(LocalDateTime.now());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setStatusCode(200);
        log.setResponseTimeMs(0);

        auditLogService.saveAuditLog(log);

        // 🧠 Map permissions and roles to GrantedAuthorities
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            // Add role itself (e.g., ROLE_ADMIN)
            authorities.add(new SimpleGrantedAuthority(role.getName()));

            // Add role's permissions (e.g., product:create, user:update)
            if (role.getPermissions() != null) {
                authorities.addAll(
                        role.getPermissions().stream()
                                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                                .collect(Collectors.toSet())
                );
            }
        }

        System.out.println("authorities  >>> " + authorities);

        // 🔁 Return new DefaultOidcUser with updated authorities
        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }
}