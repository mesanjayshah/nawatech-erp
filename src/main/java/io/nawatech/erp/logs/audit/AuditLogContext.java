package io.nawatech.erp.logs.audit;

import java.util.ArrayList;
import java.util.List;

public class AuditLogContext {

    private static final ThreadLocal<List<AuditLogDetailInfo>> context = ThreadLocal.withInitial(ArrayList::new);

    public static void add(AuditLogDetailInfo header) {
        System.out.println("✅ Adding audit log: " + header.getEntityName() + " ID=" + header.getEntityId());
        context.get().add(header);
    }

    public static List<AuditLogDetailInfo> getAndClear() {
        var list = context.get();
        context.remove();
        return list;
    }
}