package ltd.matrixstudios.skeleton.deployment.container.wrapper

import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.model.Container
import ltd.matrixstudios.skeleton.deployment.targets.DeploymentTemplate

/**
 * Class created on 2/21/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
data class ContainerData(
    val id: String,
    val model: Container,
    val target: DeploymentTemplate? = null,
    val container: CreateContainerResponse? = null
)