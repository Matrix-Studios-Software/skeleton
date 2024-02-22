package ltd.matrixstudios.skeleton

import io.ktor.server.application.*
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
import ltd.matrixstudios.skeleton.core.HttpPassthroughManagement.configureHTTP
import ltd.matrixstudios.skeleton.deployment.DeploymentService
import ltd.matrixstudios.skeleton.deployment.repository.TargetRepositoryService
import ltd.matrixstudios.skeleton.plugins.*
import ltd.matrixstudios.skeleton.paths.SkeletonRoutingManager.configureRouting

fun Application.module()
{
    configureAdministration()
    configureSerialization()
    //configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting()

    SkeletonConfigurationService.load()
    TargetRepositoryService.loadFiles()

    println(DeploymentService.dockerClient.listContainersCmd().exec().toString())
}

fun config() = SkeletonConfigurationService.config
