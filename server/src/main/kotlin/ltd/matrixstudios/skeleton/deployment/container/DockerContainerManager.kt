package ltd.matrixstudios.skeleton.deployment.container

import com.github.dockerjava.api.command.InspectContainerResponse
import com.github.dockerjava.api.model.Container
import ltd.matrixstudios.skeleton.deployment.DeploymentService
import ltd.matrixstudios.skeleton.deployment.container.wrapper.ContainerData
import ltd.matrixstudios.skeleton.deployment.targets.DeploymentTarget
import ltd.matrixstudios.skeleton.formatId

object DockerContainerManager
{
    private val containerDataCache = mutableMapOf<String, ContainerData>()

    fun listContainers(): List<Container> = DeploymentService.dockerClient.listContainersCmd().exec()

    fun getContainerInspection(containerId: String): InspectContainerResponse =
        DeploymentService.dockerClient.inspectContainerCmd(containerId).exec()

    fun killContainer(containerId: String)
    {
        DeploymentService.dockerClient.killContainerCmd(containerId).exec()
    }

    fun getContainerById(containerId: String): Container? =
        DeploymentService.dockerClient.listContainersCmd().exec().firstOrNull { it.id == containerId.formatId() }

    fun createAndInitiateContainer(imageId: String, deploymentTarget: DeploymentTarget? = null)
    {
        val creationResponse = DeploymentService.dockerClient.createContainerCmd(imageId).exec()

        DeploymentService.dockerClient.startContainerCmd(creationResponse.id).exec()

        containerDataCache[creationResponse.id] = ContainerData(
            getContainerById(creationResponse.id)!!,
            deploymentTarget,
            creationResponse
        )
    }

    fun pushContainer(containerData: ContainerData)
    {
        containerDataCache[containerData.model.id] = containerData
    }

    fun getContainerData(id: String): ContainerData?
    {
        val container = listContainers().firstOrNull { it.id == id.lowercase() }
            ?: return null

        return containerDataCache[id.lowercase()]
            ?: ContainerData(container)
    }
}