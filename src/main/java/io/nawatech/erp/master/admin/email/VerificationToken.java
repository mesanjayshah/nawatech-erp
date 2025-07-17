package io.nawatech.erp.master.admin.email;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.nawatech.erp.master.admin.Admin;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    private Admin admin;

    private LocalDateTime expiryDate;

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }

}
