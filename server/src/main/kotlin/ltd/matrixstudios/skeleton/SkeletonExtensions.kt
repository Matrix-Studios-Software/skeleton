package ltd.matrixstudios.skeleton

import io.ktor.server.application.*
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
import ltd.matrixstudios.skeleton.deployment.repository.TargetRepositoryService
import ltd.matrixstudios.skeleton.plugins.*

fun Application.module()
{
    configureAdministration()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting()

    SkeletonConfigurationService.load()
    TargetRepositoryService.loadFiles()
}

fun config() = SkeletonConfigurationService.config
