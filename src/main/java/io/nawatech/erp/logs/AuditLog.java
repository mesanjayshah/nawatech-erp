package io.nawatech.erp.logs;

import io.nawatech.erp.admin.Admin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuditEventType eventType;

    private String principal; // email or subject ID
    private String idpName;   // optional: e.g., "Okta", "Google", "Azure"

    private String httpMethod;
    private String requestUri;
    private String ipAddress;
    private LocalDateTime requestTime;
    private String userAgent;
    private String details;
    private int statusCode;
    private long responseTimeMs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin user;

}
