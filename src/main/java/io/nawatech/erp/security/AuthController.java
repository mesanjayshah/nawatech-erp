package io.nawatech.erp.security;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import io.nawatech.erp.admin.Admin;
import io.nawatech.erp.admin.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AdminService userService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        String email;

        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            email = oidcUser.getEmail();
        } else if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        } else {
            email = authentication.getName();
        }
        model.addAttribute("rolePermission", userService.getRolesAndPermissionsByEmail(email));

        return "home/index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        Object error = request.getSession().getAttribute("error");
        if (error != null) {
            model.addAttribute("error", error);
            request.getSession().removeAttribute("error");
        }
        return "auth/login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new Admin());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Admin user, Model model) {
        try {
            userService.register(user);
            model.addAttribute("message", "Registration successful. Please check your email to verify your account.");
        } catch (Exception ex) {
            model.addAttribute("error", "Registration failed: " + ex.getMessage());
        }
        return "redirect:/login";
    }


    @GetMapping("set-password")
    public String showSetPasswordForm(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        model.addAttribute("email", oidcUser.getEmail());
        return "auth/reset-password";
    }

    @PostMapping("set-password")
    public String savePassword(@RequestParam String password,
                               @AuthenticationPrincipal OidcUser oidcUser,
                               RedirectAttributes redirectAttributes) {

        String email = oidcUser.getEmail();
        Admin user = userService.findByEmail(email).orElseThrow();

        if (user.getPassword() == null) {
            userService.updatePasswordForSocialLogin(user, password);
            redirectAttributes.addFlashAttribute("message", "Password set successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Password already set.");
        }

        return "redirect:/";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                return "error/403";
            } else if (statusCode == 404) {
                return "error/404";
            } else if (statusCode == 500) {
                return "error/500";
            }
        }
        return "error/generic";
    }
}
