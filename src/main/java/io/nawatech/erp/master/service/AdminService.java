package io.nawatech.erp.master.service;

import io.nawatech.erp.master.entity.Admin;
import io.nawatech.erp.master.entity.PermissionTemplate;
import io.nawatech.erp.master.entity.RoleTemplate;
import io.nawatech.erp.master.repository.AdminRepository;
import io.nawatech.erp.master.repository.RoleTemplateRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.nawatech.erp.master.admin.email.EmailService;
import io.nawatech.erp.master.entity.VerificationToken;
import io.nawatech.erp.master.repository.VerificationTokenRepository;
import io.nawatech.erp.utils.BasicDTO;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleTemplateRepository roleRepository;
    private final HttpServletRequest request; // for IP address
    private final EmailService emailService;
    private final VerificationTokenRepository tokenRepo;


    public List<BasicDTO> findAllAdmin() {
        return adminRepository.findAllAdmin();
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin user = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (Boolean.FALSE.equals(user.getEmailVerified())) {
            throw new DisabledException("Please verify your email before logging in.");
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // Add roles as authorities (Spring expects roles to be prefixed with ROLE_)
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName())); // e.g. ROLE_USER

            // Add permissions as authorities
            role.getPermissionTemplates().forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getName())); // e.g. product:read
            });
        });

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true, true, true, true,
                authorities
        );
    }

    public void updatePasswordForSocialLogin(Admin user, String rawPassword) {
        if (user.getPassword() == null) {
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setProvider("LOCAL");
            adminRepository.save(user);
        } else {
            throw new IllegalStateException("Password already set");
        }
    }

    public void register(Admin user) {

        Set<RoleTemplate> defaultRole = Collections.singleton(roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found")));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(defaultRole);
        user.setProvider("LOCAL");
        user.setEmailVerified(false);
        adminRepository.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setAdmin(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        tokenRepo.save(verificationToken);

        String subject = "Verify your email";
        String verificationUrl = "http://localhost:8080/erp/token/verify?token=" + token;
        String body = "Click the link to verify your account: " + verificationUrl;

        emailService.sendSimpleMail(user.getEmail(), subject, body);
    }

    public Optional<Admin> findByProviderAndProviderId(String provider, String providerId) {
        return adminRepository.findByProviderAndProviderId(provider, providerId);
    }

    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

        private String getClientIp() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    public List<Admin> getUsers(){
        return adminRepository.findAll();
    }

    public Map<String, Set<String>> getRolesAndPermissionsByEmail(String email) {
        Optional<Admin> userOpt = adminRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        Admin user = userOpt.get();

        Set<String> roles = user.getRoles().stream()
                .map(RoleTemplate::getName)
                .collect(Collectors.toSet());

        Set<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissionTemplates().stream())
                .map(PermissionTemplate::getName)
                .collect(Collectors.toSet());

        Map<String, Set<String>> result = new HashMap<>();
        result.put("roles", roles);
        result.put("permissions", permissions);

        return result;
    }

}
