package io.nawatech.erp.logs.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class AuditLogDetailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private String entityId;
    private String action;
    private LocalDateTime timestamp;
    private String username;
    private String ipAddress;

    @OneToMany(mappedBy = "auditLogDetailInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditLogDetail> details;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditLogDetailInfo)) return false;
        AuditLogDetailInfo that = (AuditLogDetailInfo) o;
        return Objects.equals(entityName, that.entityName) &&
                Objects.equals(entityId, that.entityId) &&
                Objects.equals(action, that.action) &&
                Objects.equals(username, that.username) &&
                Objects.equals(ipAddress, that.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName, entityId, action, username, ipAddress);
    }
}