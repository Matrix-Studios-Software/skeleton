package ltd.matrixstudios.skeleton.redis.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import ltd.matrixstudios.skeleton.redis.RedisDatabaseManager

object RedisSpecificRoutes
{
    /**
     * Builds and runs and image
     *
     * @path /redis/container/dump
     */
    suspend fun dumpRedisRequest(call: ApplicationCall) =
        call.respond(
            RedisDatabaseManager.useThenClose { jedis ->
                return@useThenClose jedis.hgetAll("skeleton:container-bindings:")
            }
        )
}