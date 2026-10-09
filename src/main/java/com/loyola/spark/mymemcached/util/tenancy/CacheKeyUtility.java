package com.loyola.spark.mymemcached.util.tenancy;

public class CacheKeyUtility {
    private static final String KEY_FORMAT = "tenantId:%s:productId:%s";

    public static String createProductKey(String tenantId, String productId) {
        if (tenantId == null || productId == null) {
            throw new IllegalArgumentException("Tenant ID and Product ID cannot be null");
        }
        // Trim and sanitize to prevent whitespace violations in Memcached
        return String.format(KEY_FORMAT, tenantId.trim(), productId.trim());
    }
}