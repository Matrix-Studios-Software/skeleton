package ltd.matrixstudios.skeleton.deployment.container

import com.github.dockerjava.api.command.InspectContainerResponse
import com.github.dockerjava.api.model.Container
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.Ports
import ltd.matrixstudios.skeleton.deployment.DeploymentService
import ltd.matrixstudios.skeleton.deployment.container.wrapper.ContainerData
import ltd.matrixstudios.skeleton.deployment.targets.DeploymentTemplate
import ltd.matrixstudios.skeleton.formatId
import ltd.matrixstudios.skeleton.sync.ContainerBindingService

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

    fun createAndInitiateContainer(imageId: String, deploymentTemplate: DeploymentTemplate? = null)
    {
        val portBindings = mutableMapOf<Int, Int>()
        val ports = Ports()

        ports.bind(ExposedPort.tcp(25565), Ports.Binding.bindPort(25572))
        portBindings[25565] = 25572

        val creationResponse = DeploymentService.dockerClient.createContainerCmd(imageId)
            .withPortBindings(ports)
            .withIpv4Address("0.0.0.0")
            .exec()

        DeploymentService.dockerClient.startContainerCmd(creationResponse.id).exec()

        if (deploymentTemplate != null)
        {
            ContainerData(
                creationResponse.id,
                getContainerById(creationResponse.id)!!,
                deploymentTemplate,
                creationResponse
            ).apply {
                pushContainer(this, deploymentTemplate)
            }
        }
    }

    fun pushContainer(containerData: ContainerData, deploymentTemplate: DeploymentTemplate? = null)
    {
        containerDataCache[containerData.model.id] = containerData

        if (deploymentTemplate != null)
        {
            ContainerBindingService.addContainerId(deploymentTemplate.id, containerData)
        }
    }

    fun getContainerData(id: String): ContainerData?
    {
        val container = listContainers().firstOrNull { it.id == id.lowercase() }
            ?: return null

        return containerDataCache[id.lowercase()]
            ?: ContainerData(id, container)
    }
}