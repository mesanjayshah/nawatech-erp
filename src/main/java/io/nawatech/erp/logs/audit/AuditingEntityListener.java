package io.nawatech.erp.logs.audit;

import io.nawatech.erp.logs.AuditLogRepository;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class AuditingEntityListener {

    private static final Set<String> HIBERNATE_FIELDS = Set.of(
            "$$_hibernate_entityEntryHolder", "$$_hibernate_useTracker",
            "$$_hibernate_instanceId", "$$_hibernate_attributeInterceptor",
            "$$_hibernate_tracker", "$$_hibernate_previousManagedEntity",
            "$$_hibernate_nextManagedEntity", "handler", "interceptor"
    );

    @PreUpdate
    public void onUpdate(Object entity) {
        if (!(entity instanceof AuditableEntity auditable)) return;

        Map<String, Object> original = auditable.getOriginalState();
        List<AuditLogDetail> changes = new ArrayList<>();

        for (Field field : entity.getClass().getDeclaredFields()) {
            if (HIBERNATE_FIELDS.contains(field.getName())) continue;

            field.setAccessible(true);
            try {
                Object oldVal = original.get(field.getName());
                Object newVal = field.get(entity);
                if (!Objects.equals(oldVal, newVal)) {
                    AuditLogDetail detail = new AuditLogDetail();
                    detail.setFieldName(field.getName());
                    detail.setOldValue(oldVal != null ? String.valueOf(oldVal) : null);
                    detail.setNewValue(newVal != null ? String.valueOf(newVal) : null);
                    changes.add(detail);
                }
            } catch (IllegalAccessException ignored) {
            }
        }

        if (!changes.isEmpty()) {
            AuditLogDetailInfo header = new AuditLogDetailInfo();
            header.setEntityName(entity.getClass().getSimpleName());
            header.setEntityId(getEntityId(entity));
            header.setAction("UPDATE");
            header.setTimestamp(LocalDateTime.now());
            header.setUsername(getCurrentUsername());
            header.setIpAddress(getClientIpAddress());

            header.setDetails(new ArrayList<>(changes));
            for (AuditLogDetail d : changes) {
                d.setAuditLogDetailInfo(header);
            }

            AuditLogContext.add(header);
        }
    }

    @PrePersist
    public void onCreate(Object entity) {
        if (!(entity instanceof AuditableEntity)) return;

        List<AuditLogDetail> details = new ArrayList<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (HIBERNATE_FIELDS.contains(field.getName())) continue;

            field.setAccessible(true);
            try {
                Object val = field.get(entity);
                AuditLogDetail detail = new AuditLogDetail();
                detail.setFieldName(field.getName());
                detail.setOldValue(null);
                detail.setNewValue(val != null ? String.valueOf(val) : null);
                details.add(detail);
            } catch (IllegalAccessException ignored) {
            }
        }

        if (!details.isEmpty()) {
            AuditLogDetailInfo header = new AuditLogDetailInfo();
            header.setEntityName(entity.getClass().getSimpleName());
            header.setEntityId(getEntityId(entity));
            header.setAction("CREATE");
            header.setTimestamp(LocalDateTime.now());
            header.setUsername(getCurrentUsername());
            header.setIpAddress(getClientIpAddress());

            header.setDetails(new ArrayList<>(details));
            for (AuditLogDetail d : details) {
                d.setAuditLogDetailInfo(header);
            }

            AuditLogContext.add(header);
        }
    }

    @PreRemove
    public void onDelete(Object entity) {
        if (!(entity instanceof AuditableEntity)) return;

        List<AuditLogDetail> details = new ArrayList<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (HIBERNATE_FIELDS.contains(field.getName())) continue;

            field.setAccessible(true);
            try {
                Object val = field.get(entity);
                AuditLogDetail detail = new AuditLogDetail();
                detail.setFieldName(field.getName());
                detail.setOldValue(val != null ? String.valueOf(val) : null);
                detail.setNewValue(null);
                details.add(detail);
            } catch (IllegalAccessException ignored) {
            }
        }

        if (!details.isEmpty()) {
            AuditLogDetailInfo header = new AuditLogDetailInfo();
            header.setEntityName(entity.getClass().getSimpleName());
            header.setEntityId(getEntityId(entity));
            header.setAction("DELETE");
            header.setTimestamp(LocalDateTime.now());
            header.setUsername(getCurrentUsername());
            header.setIpAddress(getClientIpAddress());

            header.setDetails(new ArrayList<>(details));
            for (AuditLogDetail d : details) {
                d.setAuditLogDetailInfo(header);
            }

            AuditLogContext.add(header);
        }
    }

    private String getEntityId(Object entity) {
        try {
            Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Id.class))
                    .findFirst().orElse(null);
            if (idField != null) {
                idField.setAccessible(true);
                return String.valueOf(idField.get(entity));
            }
        } catch (IllegalAccessException ignored) {
        }
        return null;
    }

    private String getCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken token) {
            var principal = token.getPrincipal();
            Object email = principal.getAttributes().get("email");
            if (email != null) return email.toString();
            Object name = principal.getAttributes().get("name");
            if (name != null) return name.toString();
        }
        return auth != null ? auth.getName() : "anonymous";
    }

    private String getClientIpAddress() {
        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes attrs) {
            var request = attrs.getRequest();
            String ip = request.getHeader("X-Forwarded-For");
            return (ip != null && !ip.isEmpty()) ? ip : request.getRemoteAddr();
        }
        return null;
    }
}