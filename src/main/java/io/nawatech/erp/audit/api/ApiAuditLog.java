package io.nawatech.erp.audit.api;

import io.nawatech.erp.admin.Admin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ApiAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApiAuditEventType eventType;

    private String principal;
    private String idpName;

    private String httpMethod;
    private String requestUri;
    private String ipAddress;
    private LocalDateTime requestTime;
    private String userAgent;
    private String details;
    private int statusCode;
    private long responseTimeMs;

    private boolean accessGranted;       // ✅ true if the user had permission
    private String permissionChecked;    // e.g. "product:read"
    private String permissionReason;     // e.g. "permission granted by ROLE_USER"


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin user;

}
