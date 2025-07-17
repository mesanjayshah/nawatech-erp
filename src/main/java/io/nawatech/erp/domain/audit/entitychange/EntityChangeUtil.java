package io.nawatech.erp.domain.audit.entitychange;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class EntityChangeUtil {

    private EntityChangeUtil() {}

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
