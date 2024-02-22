package ltd.matrixstudios.skeleton.deployment.repository

import io.ktor.serialization.kotlinx.json.*
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
import ltd.matrixstudios.skeleton.deployment.scaling.ReplicationProperties
import ltd.matrixstudios.skeleton.deployment.targets.DeploymentTarget
import java.io.File

object TargetRepositoryService
{
    val targets = mutableMapOf<String, DeploymentTarget>()
    private val parent = File(SkeletonConfigurationService.getParentFolder(), "targets")

    fun loadFiles()
    {
        if (!parent.exists())
        {
            parent.mkdir()
        }

        val children = parent.listFiles()?.filter { it.isDirectory } ?: emptyList()

        for (child in children)
        {
            // once filtered we treat as a json file
            val templateConfig = child.listFiles()?.firstOrNull { it.isFile && it.name == "template-config.json" }
                ?: continue

            val parsedDeploymentTarget = DefaultJson.decodeFromString<DeploymentTarget>(templateConfig.readText())
            println("Found deployment target: ${parsedDeploymentTarget.id}")

            val replicationFile = child.listFiles()?.firstOrNull { it.isFile && it.name == "replication-settings.json" }

            if (replicationFile != null)
            {
                println("Found the replication settings for ${parsedDeploymentTarget.id}")
                val replicationProperties = DefaultJson.decodeFromString<ReplicationProperties>(replicationFile.readText())

                parsedDeploymentTarget.replicationProperties = replicationProperties
            }

            targets[parsedDeploymentTarget.id] = parsedDeploymentTarget
        }
    }
}