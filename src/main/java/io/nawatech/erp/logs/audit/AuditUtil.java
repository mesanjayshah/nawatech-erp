package io.nawatech.erp.logs.audit;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class AuditUtil {

    private AuditUtil() { } // prevent instantiation

    public static Map<String, Object> captureOriginalState(Object entity) {
        Map<String, Object> originalState = new HashMap<>();
        if (entity == null) return originalState;

        Class<?> clazz = entity.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    originalState.put(field.getName(), field.get(entity));
                } catch (IllegalAccessException ignored) {}
            }
            clazz = clazz.getSuperclass();
        }
        return originalState;
    }

}

