package ltd.matrixstudios.skeleton

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.server.application.*
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
import ltd.matrixstudios.skeleton.core.HttpPassthroughManagement.configureHTTP
import ltd.matrixstudios.skeleton.deployment.DeploymentService
import ltd.matrixstudios.skeleton.deployment.repository.TargetRepositoryService
import ltd.matrixstudios.skeleton.plugins.*
import ltd.matrixstudios.skeleton.paths.SkeletonRoutingManager.configureRouting

// use GSON to handle ambiguous object serialization
val GSON: Gson = GsonBuilder()
    .setPrettyPrinting()
    .serializeNulls()
    .create()

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
