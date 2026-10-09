package com.loyola.spark.mymemcached.util;

import net.spy.memcached.MemcachedClient;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SpymemcachedExample {
    public static void main(String[] args) {
        MemcachedClient client = null;
        try {
            // 1. Connect to the Memcached server (host, port)
            client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
            System.out.println("Connected to Memcached server successfully.");

            // 2. Store data (Key, Expiration in seconds, Value)
            // Setting a value with a 1-hour (3600s) expiration time
            client.set("user_session", 3600, "active_user_data_123");
            System.out.println("Data successfully cached!");

            // 3. Retrieve data by key
            Object cachedValue = client.get("user_session");
            System.out.println("Retrieved Value: " + cachedValue);

            // 4. Delete data
            client.delete("user_session");
            System.out.println("Key deleted successfully.");

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            if (client != null) {
                // 5. Always shut down the client when finished
                client.shutdown();
            }
        }
    }
}