package io.nawatech.erp.master.admin.email;

import io.nawatech.erp.master.entity.VerificationToken;
import io.nawatech.erp.master.repository.VerificationTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import io.nawatech.erp.master.entity.Admin;
import io.nawatech.erp.master.repository.AdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final VerificationTokenRepository tokenRepo;
    private final AdminRepository userRepo;

    @GetMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token, HttpServletResponse response) throws IOException {

        VerificationToken verificationToken = tokenRepo.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token"));

        if (verificationToken.isExpired()) {
            response.sendRedirect("/login?error=Verification token expired");
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        Admin user = verificationToken.getAdmin();
        user.setEmailVerified(true);
        userRepo.save(user);
        tokenRepo.delete(verificationToken);

        response.sendRedirect("/erp/login?success=Email verified successfully");
        return ResponseEntity.ok().build();
    }
}
