package io.nawatech.erp.auth;

import io.nawatech.erp.logs.AuditEventType;
import io.nawatech.erp.logs.AuditLog;
import io.nawatech.erp.logs.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.nawatech.erp.admin.Admin;
import io.nawatech.erp.admin.AdminRepository;
import io.nawatech.erp.admin.AdminService;
import io.nawatech.erp.tenant.role.Role;
import io.nawatech.erp.tenant.role.RoleRepository;
import io.nawatech.erp.util.CommonUtils;
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
    private AuditLogService auditLogService;

/*    private final AdminService userService;
    private final AdminRepository userRepository;
    private final RoleRepository roleRepository;
    private final HttpServletRequest request;*/

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        String sub = oidcUser.getSubject();
        String email = oidcUser.getEmail();
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
        AuditLog log = new AuditLog();
        log.setEventType(AuditEventType.LOGIN_SUCCESS);
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

        // 🔁 Return new DefaultOidcUser with updated authorities
        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }
}