package com.loyola.spark.mymemcached.util;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemcachedExample {
    public static void main(String[] args) {
        MemcachedClient client = null;
        try {
            // 1. Initialize client and connect to Memcached server (host:port)
            XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses("localhost:11211")
            );
            client = builder.build();

            // 2. Store data (Key, Expiration in seconds, Value)
            // Setting a value with a 1-hour (3600s) expiration time
            client.set("user_session", 3600, "active_user_data_123");
            System.out.println("Data successfully cached!");

            // 3. Retrieve data by key
            String cachedValue = client.get("user_session");
            System.out.println("Retrieved Value: " + cachedValue);

            // 4. Delete data
            client.delete("user_session");
            System.out.println("Key deleted successfully.");

        } catch (IOException e) {
            System.err.println("XMemcached builder error: " + e.getMessage());
        } catch (TimeoutException e) {
            System.err.println("Operation timeout: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Operation interrupted: " + e.getMessage());
        } catch (MemcachedException e) {
            System.err.println("Memcached internal error: " + e.getMessage());
        } finally {
            if (client != null) {
                try {
                    // 5. Always close the client connection when done
                    client.shutdown();
                } catch (IOException e) {
                    System.err.println("Error closing client: " + e.getMessage());
                }
            }
        }
    }
}