package io.nawatech.erp.domain.audit.entitychange;

import java.util.HashSet;
import java.util.Set;

public class EntityChangeContext {

    private static final ThreadLocal<Set<EntityChangeDetailInfo>> context = ThreadLocal.withInitial(HashSet::new);

    public static void add(EntityChangeDetailInfo log) {
        boolean added = context.get().add(log);
        if (added) {
            System.out.println("✅ Entity change log added: " + log.getEntityName() + " ID=" + log.getEntityId());
        } else {
            System.out.println("⚠️ Duplicate entity change log ignored: " + log.getEntityName() + " ID=" + log.getEntityId());
        }
    }

    public static Set<EntityChangeDetailInfo> getAndClear() {
        Set<EntityChangeDetailInfo> logs = context.get();
        context.remove();
        return logs;
    }
}
