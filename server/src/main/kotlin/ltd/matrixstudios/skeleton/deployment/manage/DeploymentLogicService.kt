package ltd.matrixstudios.skeleton.deployment.manage

import ltd.matrixstudios.skeleton.deployment.container.DockerContainerManager

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
}