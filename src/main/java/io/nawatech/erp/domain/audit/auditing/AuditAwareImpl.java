package io.nawatech.erp.domain.audit.auditing;

import io.nawatech.erp.master.admin.Admin;
import io.nawatech.erp.master.admin.AdminService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<Admin> {

    @Resource
    AdminService adminService;

    @Override
    public Optional<Admin> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        Authentication loggedInUser  =  SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminService.findByUsername(loggedInUser.getName());
        System.out.println(admin + " log admin here");
        return Optional.ofNullable(admin);
    }
}
