package io.nawatech.erp.master.admin;

import io.nawatech.erp.master.entity.RoleTemplate;
import jakarta.persistence.*;
import lombok.*;
import io.nawatech.erp.domain.tenant.role.Role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String username; // not stored, only for UI purpose

    private String password;

    private String provider;      // GOOGLE, LOCAL, etc.
    private String providerId;    // OAuth2 subfield

    private String name;
    private String givenName;
    private String familyName;
    private String pictureUrl;
    private Boolean emailVerified;
    private String locale;

    private String accessToken;
    private String refreshToken;

    private Long loginCount;
    private String lastLoginIp;
    private LocalDateTime lastLoginAt;
    private LocalDateTime lockTime;

    @Column(columnDefinition = "boolean default false")
    private boolean isEnabled;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(columnDefinition = "boolean default false")
    private boolean isBlocked;

    private boolean isAccountNonLocked;

    private int failedAttempt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleTemplate> roles = new HashSet<>();

    @PrePersist
    @PreUpdate
    public void preSave() {
        if (this.username == null && this.email != null) {
            this.username = this.email;
        }
    }
}
