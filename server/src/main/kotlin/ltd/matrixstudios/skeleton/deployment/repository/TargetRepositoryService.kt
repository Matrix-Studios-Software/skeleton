package ltd.matrixstudios.skeleton.deployment.repository

import io.ktor.serialization.kotlinx.json.*
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
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

            targets[parsedDeploymentTarget.id] = parsedDeploymentTarget
        }
    }
}