package nawatech.io.erp;

import nawatech.io.erp.tenant.role.Permission;
import nawatech.io.erp.tenant.role.PermissionRepository;
import nawatech.io.erp.tenant.role.Role;
import nawatech.io.erp.tenant.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class NawatechErpApplication implements CommandLineRunner {

    public NawatechErpApplication(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(NawatechErpApplication.class, args);
	}

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;

	@Override
	public void run(String... args) {
        // Create permissions
/*        Permission readPermission = permissionRepository.save(new Permission("READ_DASHBOARD"));
        Permission writePermission = permissionRepository.save(new Permission("WRITE_USER"));

        // Create roles and assign permissions
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setPermissions(Set.of(readPermission, writePermission));
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setPermissions(Set.of(readPermission));
        roleRepository.save(userRole);*/
	}


}
