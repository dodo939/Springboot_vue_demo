package io.github.dodo939.utils;

import java.util.Map;

public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL_CLAIM = new ThreadLocal<>();

    public static Map<String, Object> getClaim() {
        return THREAD_LOCAL_CLAIM.get();
    }

    public static void setClaim(Map<String, Object> value) {
        THREAD_LOCAL_CLAIM.set(value);
    }

    public static void remove() {
        THREAD_LOCAL_CLAIM.remove();
    }
}
