package ltd.matrixstudios.skeleton.deployment.container

import com.github.dockerjava.api.command.InspectContainerResponse
import com.github.dockerjava.api.model.Container
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.Ports
import ltd.matrixstudios.skeleton.config
import ltd.matrixstudios.skeleton.deployment.DeploymentService
import ltd.matrixstudios.skeleton.deployment.container.wrapper.ContainerData
import ltd.matrixstudios.skeleton.deployment.manage.DeploymentRequest
import ltd.matrixstudios.skeleton.deployment.targets.DeploymentTemplate
import ltd.matrixstudios.skeleton.formatId
import ltd.matrixstudios.skeleton.log
import ltd.matrixstudios.skeleton.network.type.PropertiesNetworkConfiguration
import ltd.matrixstudios.skeleton.sync.ContainerBindingService

object DockerContainerManager
{
    private val containerDataCache = mutableMapOf<String, ContainerData>()

    fun listContainers(): List<Container> = DeploymentService.dockerClient.listContainersCmd().exec()

    fun getContainerInspection(containerId: String): InspectContainerResponse =
        DeploymentService.dockerClient.inspectContainerCmd(containerId).exec()

    fun killContainer(containerId: String) =
        DeploymentService.dockerClient.killContainerCmd(containerId).exec()

    fun deleteContainer(containerId: String) =
        DeploymentService.dockerClient.removeContainerCmd(containerId).exec()

    fun getContainerById(containerId: String): Container? =
        DeploymentService.dockerClient.listContainersCmd().exec().firstOrNull { it.id == containerId }

    fun createAndInitiateContainer(imageId: String, deploymentTemplate: DeploymentTemplate? = null, request: DeploymentRequest? = null)
    {
        val portBindings = mutableMapOf<Int, Int>()
        val ports = Ports()

        if (request != null)
        {
            ports.bind(ExposedPort.tcp(request.exposedPort), Ports.Binding.bindPort(request.bindedPort))
            portBindings[request.exposedPort] = request.bindedPort
        }

        val creationResponse = DeploymentService.dockerClient.createContainerCmd(imageId)
            .withPortBindings(ports)
            .withIpv4Address(request?.hostName ?: "0.0.0.0")
            .exec()

        val propertiesLocation = config().getNetworkConfigurationPath()

        if (propertiesLocation != "None" && request != null)
        {
            val key = config().getNetworkConfigurationProperty()

            PropertiesNetworkConfiguration(key).also {
                it.setPortInformation(key, request.bindedPort)
            }
        }

        try
        {
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
        } catch (e: Exception)
        {
            log("[Deployment] Caught an exception while opening a container: ${e.message}")
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