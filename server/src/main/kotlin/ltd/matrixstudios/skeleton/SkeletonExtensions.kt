package ltd.matrixstudios.skeleton

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.server.application.*
import ltd.matrixstudios.skeleton.automation.routine.AutomaticScaleRoutine
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService
import ltd.matrixstudios.skeleton.core.HttpPassthroughManagement.configureHTTP
import ltd.matrixstudios.skeleton.deployment.DeploymentService
import ltd.matrixstudios.skeleton.deployment.repository.RepositoryTemplateService
import ltd.matrixstudios.skeleton.plugins.*
import ltd.matrixstudios.skeleton.redis.RedisDatabaseManager
import ltd.matrixstudios.skeleton.route.SkeletonRoutingManager.configureRouting
import ltd.matrixstudios.skeleton.health.ContainerHealthRoutine

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

    RedisDatabaseManager.load("localhost", 6379, null)
    SkeletonConfigurationService.load()
    RepositoryTemplateService.loadFiles()

    ContainerHealthRoutine.start()
    AutomaticScaleRoutine.startScaling()

    println(DeploymentService.dockerClient.listContainersCmd().exec().toString())
}

fun config() = SkeletonConfigurationService.config

fun String.formatId(): String = "sha256:$this"

fun log(message: String)
{
    println("[Skeleton] $message")
}
