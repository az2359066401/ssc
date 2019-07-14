package com.vivo.bigdata.heatmap.util;

import org.apache.log4j.Logger;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.ReentrantLock;

public class RedisUtil {


    protected static ReentrantLock lockPool = new ReentrantLock();
    protected static ReentrantLock lockJedis = new ReentrantLock();
    private static final Logger LOGGER = Logger.getLogger(RedisUtil.class);
    private static final String IP =  "s40";
    private static final int PORT = 6379;
    private static final String PASSWORD = null;
    private static int MAX_ACTIVE = 200;
    private static int MAX_IDLE = 8;
    private static int MAX_WAIT = 100000;
    private static int TIMEOUT = 3000;
    private static boolean TEST_ON_BORROW = false;
    private static JedisPool jedisPool = null;
    public static final int EXRP_HOUR = 3600;
    public static final int EXRP_DAY = 86400;
    public static final int EXRP_MONTH = 2592000;
    public static Jedis jedis;

    private static void initialPool()
    {
        try
        {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            LOGGER.info("redis {}  --  {} --  {} " +  IP + PORT + TIMEOUT);
            jedisPool = new JedisPool(config, IP, PORT, TIMEOUT);
        }
        catch (Exception e)
        {
            LOGGER.error("First create JedisPool error : " + e);
        }
    }

    private static synchronized void poolInit()
    {
        if (jedisPool == null) {
            initialPool();
        }
    }

    public static synchronized Jedis getJedis()
    {
        poolInit();
        jedis = null;
        try
        {
            if (jedisPool != null)
            {
                jedis = jedisPool.getResource();
                try
                {
                    jedis.auth(PASSWORD);
                }
                catch (Exception localException1) {}
            }
            return jedis;
        }
        catch (Exception e)
        {
            LOGGER.error("Get jedis error : " + e);
            return null;
        }
    }

    public static synchronized void set(String key, String value)
    {
        try
        {
            value = StringUtils.isBlank(value) ? "" : value;
            Jedis jedis = getJedis();
            jedis.set(key, value);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Set key error : " + e);
        }
    }

    public static synchronized void set(byte[] key, byte[] value)
    {
        try
        {
            Jedis jedis = getJedis();
            jedis.set(key, value);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Set key error : " + e);
        }
    }

    public static synchronized void set(String key, String value, int seconds)
    {
        try
        {
            value = StringUtils.isBlank(value) ? "" : value;
            Jedis jedis = getJedis();
            jedis.setex(key, seconds, value);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Set keyex error : " + e);
        }
    }

    public static synchronized void set(byte[] key, byte[] value, int seconds)
    {
        try
        {
            Jedis jedis = getJedis();
            jedis.set(key, value);
            jedis.expire(key, seconds);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Set key error : " + e);
        }
    }

    public static synchronized void setExpire(String key, int seconds)
    {
        try
        {
            Jedis jedis = getJedis();
            jedis.expire(key, seconds);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Set key error : " + e);
        }
    }

    public static synchronized String get(String key)
    {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public static synchronized byte[] get(byte[] key)
    {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        byte[] value = jedis.get(key);
        jedis.close();
        return value;
    }

    public static synchronized void remove(String key)
    {
        try
        {
            Jedis jedis = getJedis();
            jedis.del(key);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Remove keyex error : " + e);
        }
    }

    public static synchronized void remove(byte[] key)
    {
        try
        {
            Jedis jedis = getJedis();
            jedis.del(key);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Remove byte keyex error : " + e);
        }
    }

    public static synchronized void lpush(String key, String... strings)
    {
        try
        {
            Jedis jedis = getJedis();
            jedis.lpush(key, strings);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("lpush error : " + e);
        }
    }

    public static synchronized void lrem(String key, long count, String value)
    {
        try
        {
            Jedis jedis = getJedis();
            jedis.lrem(key, count, value);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("lpush error : " + e);
        }
    }

    public static synchronized void sadd(String key, String value, int seconds)
    {
        try
        {
            Jedis jedis = getJedis();
            jedis.sadd(key, new String[] { value });
            jedis.expire(key, seconds);
            jedis.close();
        }
        catch (Exception e)
        {
            LOGGER.error("sadd error : " + e);
        }
    }

    public static synchronized Long incr(String key)
    {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        long value = jedis.incr(key).longValue();
        jedis.close();
        return Long.valueOf(value);
    }

    public static synchronized Long decr(String key)
    {
        Jedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        long value = jedis.decr(key).longValue();
        jedis.close();
        return Long.valueOf(value);
    }
}