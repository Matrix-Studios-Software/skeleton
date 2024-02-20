package ltd.matrixstudios.skeleton.configuration

import ltd.matrixstudios.amber.AmberConfigurationService
import ltd.matrixstudios.skeleton.SkeletonServer

object SkeletonConfigurationService
{
    lateinit var config: SkeletonConfiguration

    fun load()
    {
        AmberConfigurationService.make(
            SkeletonServer::class.java.getProtectionDomain().codeSource.location.path,
            "ltd.matrixstudios.skeleton",
            true
        )

        config = AmberConfigurationService.from(SkeletonConfiguration::class.java, "skeleton-configuration.yaml")
    }
}