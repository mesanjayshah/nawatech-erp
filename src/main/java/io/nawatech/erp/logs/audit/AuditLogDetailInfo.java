package io.nawatech.erp.logs.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class AuditLogDetailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private String entityId;
    private String action;
    private String username;
    private String ipAddress;
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "auditLogDetailInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditLogDetail> details = new ArrayList<>();
}