package ltd.matrixstudios.skeleton.deployment.repository

import ltd.matrixstudios.skeleton.configuration.SkeletonConfiguration
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
import ltd.matrixstudios.skeleton.deployment.targets.DeploymentTarget
import java.io.File

object TargetRepositoryService
{
    val targets = mutableMapOf<String, DeploymentTarget>()
    private val parent = File(SkeletonConfigurationService.getParentFolder(), "targets")

    fun loadFiles()
    {

    }
}