package ltd.matrixstudios.skeleton.deployment.manage

import ltd.matrixstudios.skeleton.deployment.container.DockerContainerManager
import ltd.matrixstudios.skeleton.deployment.repository.RepositoryTemplateService
import java.util.concurrent.ThreadLocalRandom

/**
 * Class created on 2/22/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
object DeploymentLogicService
{
    fun launchUsingExclusivelyDocker(id: String)
    {
        DockerContainerManager.createAndInitiateContainer(id)
    }

    fun launchWithDeploymentRequest(id: String, request: DeploymentRequest)
    {
        val template = RepositoryTemplateService.templates[request.templateId]
            ?: return

        DockerContainerManager.createAndInitiateContainer(id, template, request)
    }
}