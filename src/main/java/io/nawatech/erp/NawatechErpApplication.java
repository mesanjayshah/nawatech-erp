package io.nawatech.erp;

import io.nawatech.erp.admin.Admin;
import io.nawatech.erp.auditing.AuditAwareImpl;
import io.nawatech.erp.tenant.role.Permission;
import io.nawatech.erp.tenant.role.PermissionRepository;
import io.nawatech.erp.tenant.role.Role;
import io.nawatech.erp.tenant.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class NawatechErpApplication implements CommandLineRunner {

    public NawatechErpApplication(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(NawatechErpApplication.class, args);
	}

	@Bean
	AuditorAware<Admin> auditorProvider() {
		return new AuditAwareImpl();
	}

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;

	@Override
	public void run(String... args) {
        if (permissionRepository.count() == 0 && roleRepository.count() == 0) {
            Permission readPermission = permissionRepository.save(new Permission("dashboard:read"));
            Permission writePermission = permissionRepository.save(new Permission("user:write"));
            Permission readProduct = permissionRepository.save(new Permission("product:read"));
            Permission createProduct = permissionRepository.save(new Permission("product:create"));
            Permission updateProduct = permissionRepository.save(new Permission("product:update"));
            Permission deleteProduct = permissionRepository.save(new Permission("product:delete"));

            // Create roles and assign permissions
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole.setPermissions(Set.of(readPermission, writePermission, readProduct, createProduct, updateProduct, deleteProduct));
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole.setPermissions(Set.of(readPermission, readProduct));
            roleRepository.save(userRole);
        }
	}

}
