package ltd.matrixstudios.skeleton.configuration

import ltd.matrixstudios.amber.AmberConfigurationService
import ltd.matrixstudios.skeleton.SkeletonServer
import java.io.File

object SkeletonConfigurationService
{
    lateinit var config: SkeletonConfiguration

    fun load()
    {
        val parent = File(SkeletonServer::class.java.getProtectionDomain().codeSource.location.path).parentFile

        println("Loading configuration file into path: ${parent.path}")

        AmberConfigurationService.make(
            parent.path.replace("%20", " "),
            "ltd.matrixstudios.skeleton",
            true
        )

        config = AmberConfigurationService.from(SkeletonConfiguration::class.java, "skeleton-configuration.yaml")
    }

    fun getParentFolder(): File
    {
        val path = config.getTemplatePath()

        return if (path == "JAR_PATH")
        {
            File(SkeletonServer::class.java.getProtectionDomain().codeSource.location.path).parentFile
        } else
        {
            File(path)
        }
    }
}