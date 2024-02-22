package ltd.matrixstudios.skeleton.deployment.container

import com.github.dockerjava.api.command.InspectContainerResponse
import com.github.dockerjava.api.model.Container
import ltd.matrixstudios.skeleton.deployment.DeploymentService
import ltd.matrixstudios.skeleton.deployment.container.wrapper.ContainerData

object DockerContainerManager
{
    val containerDataCache = mutableMapOf<String, ContainerData>()

    fun listContainers(): List<Container> = DeploymentService.dockerClient.listContainersCmd().exec()

    fun getContainerInspection(containerId: String): InspectContainerResponse =
        DeploymentService.dockerClient.inspectContainerCmd(containerId).exec()

    fun killContainer(containerId: String)
    {
        DeploymentService.dockerClient.killContainerCmd(containerId).exec()
    }

    fun getContainerData(id: String): ContainerData?
    {
        val container = listContainers().firstOrNull { it.id == id.lowercase() }
            ?: return null

        return containerDataCache[id.lowercase()]
            ?: ContainerData(container)
    }
}