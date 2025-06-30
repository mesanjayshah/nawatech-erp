package io.nawatech.erp.logs;

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

    private String principal;
    private String httpMethod;
    private String requestUri;
    private String ipAddress;
    private LocalDateTime requestTime;
    private String userAgent;
    private int statusCode;
    private long responseTimeMs;

    // getters & setters
}
