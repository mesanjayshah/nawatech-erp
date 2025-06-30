package nawatech.io.erp.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService userService;

    @PreAuthorize("hasAuthority('dashboard:read')")
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin Dashboard Accessed Successfully!";
    }

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/users")
    public ResponseEntity<List<Admin>> getAllUsers() {
        List<Admin> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('auth:read')")
    @GetMapping("/users/{email}")
    public ResponseEntity<Map<String, Set<String>>> getRolesPermissions(@PathVariable String email) {
        return ResponseEntity.ok(userService.getRolesAndPermissionsByEmail(email));
    }
}
