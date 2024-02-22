package ltd.matrixstudios.skeleton.deployment.container.wrapper

import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.model.Container
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ltd.matrixstudios.skeleton.deployment.targets.DeploymentTarget

/**
 * Class created on 2/21/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
data class ContainerData(
    val model: Container,
    val target: DeploymentTarget? = null,
    val container: CreateContainerResponse? = null
)