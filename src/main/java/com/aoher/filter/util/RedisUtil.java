package com.aoher.filter.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public enum RedisUtil {

    INSTANCE;

    private final JedisPool pool;

    RedisUtil() {
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
    }

    public void sadd(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sadd(key, value);
        }
    }

    public void srem(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.srem(key, value);
        }
    }

    public boolean sismember(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.sismember(key, value);
        }
    }
}
