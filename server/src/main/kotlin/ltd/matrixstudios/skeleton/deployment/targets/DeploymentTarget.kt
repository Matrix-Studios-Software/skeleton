package ltd.matrixstudios.skeleton.deployment.targets

import kotlinx.serialization.Serializable
import ltd.matrixstudios.skeleton.deployment.scaling.ReplicationProperties
import java.io.File

@Serializable
data class DeploymentTarget(
    val id: String,
    val name: String,
    val space: Int,
    var directory: String? = null,
    var replicationProperties: ReplicationProperties? = null,
    val containers: MutableList<String> = mutableListOf()
)
{
    fun getDockerfile(): File = File(directory!!).listFiles().first { it.isFile && it.name == "Dockerfile" }
}