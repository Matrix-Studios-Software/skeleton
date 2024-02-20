package ltd.matrixstudios.skeleton

import io.ktor.server.application.*
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
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
}
