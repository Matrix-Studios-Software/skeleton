package ltd.matrixstudios.skeleton.automation

import ltd.matrixstudios.skeleton.deployment.manage.DeploymentLogicService
import ltd.matrixstudios.skeleton.deployment.manage.DeploymentRequest
import ltd.matrixstudios.skeleton.formatId

object AutomaticScaleService
{
    val requirements = mutableMapOf<String, AutomaticScaleRequirement>()
    fun registerRequirement(scaleRequirement: AutomaticScaleRequirement)
    {
        requirements[scaleRequirement.id] = scaleRequirement
    }

    fun scale(replications: Int, image: String, request: DeploymentRequest)
    {
        for (int in 0 until replications)
        {
            DeploymentLogicService.launchWithDeploymentRequest(image.formatId(), request)
        }
    }
}