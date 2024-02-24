package ltd.matrixstudios.skeleton.redis

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

object RedisDatabaseManager
{
    private lateinit var pool: JedisPool
    fun load(host: String, port: Int, password: String?)
    {
        pool = JedisPool(GenericObjectPoolConfig(), host, port, 2000, password)
    }

    fun <T> useThenClose(action: (Jedis) -> T) = pool.resource.use {
        action.invoke(it)
    }

}