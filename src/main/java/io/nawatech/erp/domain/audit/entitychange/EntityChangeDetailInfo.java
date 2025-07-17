package io.nawatech.erp.domain.audit.entitychange;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class EntityChangeDetailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private String entityId;
    private String action; // CREATE, UPDATE, DELETE
    private LocalDateTime timestamp;
    private String username;
    private String ipAddress;

    @OneToMany(mappedBy = "entityChangeDetailInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityChangeDetail> details;

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityChangeDetailInfo that)) return false;
        return Objects.equals(entityName, that.entityName) &&
                Objects.equals(entityId, that.entityId) &&
                Objects.equals(action, that.action) &&
                Objects.equals(username, that.username) &&
                Objects.equals(ipAddress, that.ipAddress) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName, entityId, action, username, ipAddress, timestamp);
    }*/
}
