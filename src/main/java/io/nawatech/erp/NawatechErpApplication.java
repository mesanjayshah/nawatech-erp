package io.nawatech.erp;

import io.nawatech.erp.master.admin.Admin;
import io.nawatech.erp.domain.audit.auditing.AuditAwareImpl;
import io.nawatech.erp.master.entity.PermissionTemplate;
import io.nawatech.erp.master.entity.RoleTemplate;
import io.nawatech.erp.master.repository.PermissionTemplateRepository;
import io.nawatech.erp.master.repository.RoleTemplateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Set;

@EnableAsync
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class NawatechErpApplication implements CommandLineRunner {


    public static void main(String[] args) {
		SpringApplication.run(NawatechErpApplication.class, args);
	}

	@Bean
	AuditorAware<Admin> auditorProvider() {
		return new AuditAwareImpl();
	}

    private final RoleTemplateRepository roleTemplateRepository;
    private final PermissionTemplateRepository permissionTemplateRepository;

    public NawatechErpApplication(RoleTemplateRepository roleTemplateRepository, PermissionTemplateRepository permissionTemplateRepository) {
        this.roleTemplateRepository = roleTemplateRepository;
        this.permissionTemplateRepository = permissionTemplateRepository;
    }

    @Override
	public void run(String... args) {
        if (permissionTemplateRepository.count() == 0 && roleTemplateRepository.count() == 0) {
            PermissionTemplate readPermission = permissionTemplateRepository.save(new PermissionTemplate("dashboard:read"));
            PermissionTemplate writePermission = permissionTemplateRepository.save(new PermissionTemplate("user:write"));
            PermissionTemplate readProduct = permissionTemplateRepository.save(new PermissionTemplate("product:read"));
            PermissionTemplate createProduct = permissionTemplateRepository.save(new PermissionTemplate("product:create"));
            PermissionTemplate updateProduct = permissionTemplateRepository.save(new PermissionTemplate("product:update"));
            PermissionTemplate deleteProduct = permissionTemplateRepository.save(new PermissionTemplate("product:delete"));

            // Create roles and assign permissions
            RoleTemplate adminRole = new RoleTemplate();
            adminRole.setName("ROLE_ADMIN");
            adminRole.setPermissionTemplates(Set.of(readPermission, writePermission, readProduct, createProduct, updateProduct,
                    deleteProduct));
            roleTemplateRepository.save(adminRole);

            RoleTemplate userRole = new RoleTemplate();
            userRole.setName("ROLE_USER");
            userRole.setPermissionTemplates(Set.of(readPermission, readProduct));
            roleTemplateRepository.save(userRole);
        }
	}

}
