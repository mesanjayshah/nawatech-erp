package nawatech.io.erp.auth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nawatech.io.erp.admin.Admin;
import nawatech.io.erp.admin.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AdminService userService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "success", required = false) String success,
                            Model model) {

        if (error != null) {
            model.addAttribute("error", error);
        }
        if (success != null) {
            model.addAttribute("success", success);
        }
        return "login"; // login.html
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new Admin());
        return "register";
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
        return "error/generic"; // fallback
    }
}
