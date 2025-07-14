package io.nawatech.erp.logs.audit;

import java.util.HashSet;
import java.util.Set;

public class AuditLogContext {

    private static final ThreadLocal<Set<AuditLogDetailInfo>> context = ThreadLocal.withInitial(HashSet::new);

    public static void add(AuditLogDetailInfo header) {
        boolean added = context.get().add(header);
        if (added) {
            System.out.println("✅ Audit log added: " + header.getEntityName() + " ID=" + header.getEntityId());
        } else {
            System.out.println("⚠️ Duplicate audit log ignored: " + header.getEntityName() + " ID=" + header.getEntityId());
        }
    }

    public static Set<AuditLogDetailInfo> getAndClear() {
        Set<AuditLogDetailInfo> logs = context.get();
        context.remove();
        return logs;
    }
}