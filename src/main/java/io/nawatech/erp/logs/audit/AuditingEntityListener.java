package io.nawatech.erp.logs.audit;

import io.nawatech.erp.product.Product;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

public class AuditingEntityListener {

    @PreUpdate
    public void onUpdate(Object entity) {
        if (!(entity instanceof Product)) return;

        var auditable = (AuditableEntity) entity;
        Map<String, Object> original = auditable.getOriginalState();
        List<AuditLogDetail> changes = new ArrayList<>();

        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object oldVal = original.get(field.getName());
                Object newVal = field.get(entity);
                if (!Objects.equals(oldVal, newVal)) {
                    AuditLogDetail detail = new AuditLogDetail();
                    detail.setFieldName(field.getName());
                    detail.setOldValue(String.valueOf(oldVal));
                    detail.setNewValue(String.valueOf(newVal));
                    changes.add(detail);
                }
            } catch (IllegalAccessException ignored) {}
        }

        if (!changes.isEmpty()) {
            AuditLogDetailInfo header = new AuditLogDetailInfo();
            header.setEntityName(entity.getClass().getSimpleName());
            header.setEntityId(auditable.getId() + "");
            header.setAction("UPDATE");
            header.setTimestamp(LocalDateTime.now());
            header.setUsername("system");
            header.setIpAddress("127.0.0.1");

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
            field.setAccessible(true);
            try {
                Object val = field.get(entity);
                AuditLogDetail detail = new AuditLogDetail();
                detail.setFieldName(field.getName());
                detail.setOldValue(null);
                detail.setNewValue(String.valueOf(val));
                details.add(detail);
            } catch (IllegalAccessException ignored) {}
        }

        if (!details.isEmpty()) {
            AuditLogDetailInfo header = new AuditLogDetailInfo();
            header.setEntityName(entity.getClass().getSimpleName());
            header.setEntityId(getEntityId(entity));
            header.setAction("CREATE");
            header.setTimestamp(LocalDateTime.now());
            header.setUsername(getCurrentUsername());
            header.setIpAddress(getClientIpAddress());

            // ✅ Use defensive copy
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
            field.setAccessible(true);
            try {
                Object val = field.get(entity);
                AuditLogDetail detail = new AuditLogDetail();
                detail.setFieldName(field.getName());
                detail.setOldValue(String.valueOf(val));
                detail.setNewValue(null);
                details.add(detail);
            } catch (IllegalAccessException ignored) {}
        }

        if (!details.isEmpty()) {
            AuditLogDetailInfo header = new AuditLogDetailInfo();
            header.setEntityName(entity.getClass().getSimpleName());
            header.setEntityId(getEntityId(entity));
            header.setAction("DELETE");
            header.setTimestamp(LocalDateTime.now());
            header.setUsername(getCurrentUsername());
            header.setIpAddress(getClientIpAddress());

            // ✅ Use defensive copy
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
        } catch (IllegalAccessException ignored) {}
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
