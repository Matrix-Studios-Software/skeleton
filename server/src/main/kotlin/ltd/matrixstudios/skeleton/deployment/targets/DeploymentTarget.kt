package ltd.matrixstudios.skeleton.deployment.targets

import kotlinx.serialization.Serializable
import ltd.matrixstudios.skeleton.deployment.scaling.ReplicationProperties

@Serializable
data class DeploymentTarget(
    val id: String,
    val name: String,
    val command: String,
    val space: Int,
    val directory: String? = null,
    val performanceProperties: ReplicationProperties? = null,
)