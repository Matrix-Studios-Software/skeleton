package ltd.matrixstudios.skeleton.sync

import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.skeleton.GSON
import ltd.matrixstudios.skeleton.deployment.container.wrapper.ContainerData
import ltd.matrixstudios.skeleton.redis.RedisDatabaseManager
import java.lang.reflect.Type

object ContainerBindingService
{
    private val type: Type =
        object : TypeToken<MutableMap<String, ContainerData>>()
        {}.type

    fun getContainerIdsFromTarget(targetId: String): MutableMap<String, ContainerData> =
        RedisDatabaseManager.useThenClose {
            val list = it.hget(
                "skeleton:container-bindings:",
                targetId.lowercase()
            )

            GSON.fromJson(list, type)
        }

    fun addContainerId(targetId: String, data: ContainerData) = RedisDatabaseManager.useThenClose { jedis ->
        jedis.hset(
            "skeleton:container-bindings:",
            targetId.lowercase(),
            GSON.toJson(
                getContainerIdsFromTarget(targetId).also {
                    it[data.id] = data
                },
                type
            )
        )
    }
}