package nawatech.io.erp.auth;

import jakarta.servlet.http.HttpServletRequest;
import nawatech.io.erp.admin.Admin;
import nawatech.io.erp.admin.AdminRepository;
import nawatech.io.erp.admin.AdminService;
import nawatech.io.erp.tenant.role.Role;
import nawatech.io.erp.tenant.role.RoleRepository;
import nawatech.io.erp.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private AdminService userService;
    @Autowired
    private AdminRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private HttpServletRequest request;

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

        Set<Role> defaultRole = Collections.singleton(roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found")));

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
            user.setRoles(defaultRole);
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

        userRepository.save(user);
        return oidcUser;
    }

}
