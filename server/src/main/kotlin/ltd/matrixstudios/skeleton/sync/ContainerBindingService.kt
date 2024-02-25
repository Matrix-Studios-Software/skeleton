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

    fun getContainerIdsFromTemplate(templateId: String): MutableMap<String, ContainerData> =
        RedisDatabaseManager.useThenClose { jedis ->
            val result = mutableMapOf<String, ContainerData>()

            val list = GSON.fromJson<MutableMap<String, ContainerData>>(
                jedis.hget(
                    "skeleton:container-bindings:",
                    templateId.lowercase()
                ),
                type
            )

            list?.entries?.forEach {
                result[it.key] = it.value
            }

            return@useThenClose result
        }

    fun addContainerId(templateId: String, data: ContainerData) = RedisDatabaseManager.useThenClose { jedis ->
        jedis.hset(
            "skeleton:container-bindings:",
            templateId.lowercase(),
            GSON.toJson(
                getContainerIdsFromTemplate(templateId).also {
                    it[data.id] = data
                },
                type
            )
        )
    }
}