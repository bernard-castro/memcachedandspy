package com.loyola.spark.mymemcached.util.tenancy;

import net.spy.memcached.MemcachedClient;
import java.net.InetSocketAddress;
import java.io.IOException;

public class CacheService {

    private MemcachedClient client;

    public CacheService() throws IOException {
        // Connect to your Memcached server (default port is 11211)
        this.client = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
    }

    // Helper method to generate the composite key safely
    private String buildProductKey(String tenantId, String productId) {
        if (tenantId == null || productId == null) {
            throw new IllegalArgumentException("Tenant ID and Product ID cannot be null");
        }
        // Example output: "tenantId:t100:productId:p500"
        return String.format("tenantId:%s:productId:%s", tenantId.trim(), productId.trim());
    }

    // Save product data to Memcached
    public void saveProduct(String tenantId, String productId, int ttlSeconds, Object productData) {
        String cacheKey = buildProductKey(tenantId, productId);
        // set() is asynchronous. Use add() or replace() if specific behavior is needed.
        client.set(cacheKey, ttlSeconds, productData);
    }

    // Retrieve product data from Memcached
    public Object getProduct(String tenantId, String productId) {
        String cacheKey = buildProductKey(tenantId, productId);
        return client.get(cacheKey);
    }

    // Evict/Delete product from Memcached
    public void deleteProduct(String tenantId, String productId) {
        String cacheKey = buildProductKey(tenantId, productId);
        client.delete(cacheKey);
    }

    public void shutdown() {
        if (client != null) {
            client.shutdown();
        }
    }
}