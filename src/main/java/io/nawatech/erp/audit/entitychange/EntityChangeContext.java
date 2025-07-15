package io.nawatech.erp.audit.entitychange;

import java.util.HashSet;
import java.util.Set;

public class EntityChangeContext {

    private static final ThreadLocal<Set<EntityChangeLog>> context = ThreadLocal.withInitial(HashSet::new);

    public static void add(EntityChangeLog log) {
        boolean added = context.get().add(log);
        if (added) {
            System.out.println("✅ Entity change log added: " + log.getEntityName() + " ID=" + log.getEntityId());
        } else {
            System.out.println("⚠️ Duplicate entity change log ignored: " + log.getEntityName() + " ID=" + log.getEntityId());
        }
    }

    public static Set<EntityChangeLog> getAndClear() {
        Set<EntityChangeLog> logs = context.get();
        context.remove();
        return logs;
    }
}
