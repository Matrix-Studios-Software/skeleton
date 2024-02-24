package ltd.matrixstudios.skeleton.deployment.repository

import io.ktor.serialization.kotlinx.json.*
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
import ltd.matrixstudios.skeleton.deployment.image.DockerImageManager
import ltd.matrixstudios.skeleton.deployment.scaling.ReplicationProperties
import ltd.matrixstudios.skeleton.deployment.targets.DeploymentTemplate
import java.io.File

object RepositoryTemplateService
{
    val templates = mutableMapOf<String, DeploymentTemplate>()
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

            val parsedDeploymentTemplate = DefaultJson.decodeFromString<DeploymentTemplate>(templateConfig.readText())
            println("Found deployment target: ${parsedDeploymentTemplate.id}")

            val replicationFile = child.listFiles()?.firstOrNull { it.isFile && it.name == "replication-settings.json" }

            if (replicationFile != null)
            {
                println("Found the replication settings for ${parsedDeploymentTemplate.id}")
                val replicationProperties = DefaultJson.decodeFromString<ReplicationProperties>(replicationFile.readText())

                parsedDeploymentTemplate.replicationProperties = replicationProperties
            }

            // update directory
            parsedDeploymentTemplate.directory = child.path

            templates[parsedDeploymentTemplate.id] = parsedDeploymentTemplate
        }

        for (target in templates.values)
        {
            val image = DockerImageManager.listImages().firstOrNull { it.repoTags.contains(target.id + ":1.0") }

            // already has an image setup so you dont need to make another
            if (image != null)
            {
                continue
            } else
            {
                if (target.directory != null)
                {
                    val id = DockerImageManager.createImage(target.getDockerfile())
                    DockerImageManager.tagImage(id, target.id, "1.0")
                }
            }
        }
    }
}