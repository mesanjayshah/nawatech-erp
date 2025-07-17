package io.nawatech.erp.master.security.permission;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class PermissionAuditContext {
    private String permissionChecked;
    private boolean accessGranted = true; // default to true unless a check fails
    private String permissionReason;
}
