package ltd.matrixstudios.skeleton.route

import io.ktor.client.engine.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ltd.matrixstudios.skeleton.deployment.container.routing.ContainerSpecificRoutes
import ltd.matrixstudios.skeleton.deployment.image.routing.ImageSpecificRoutes
import ltd.matrixstudios.skeleton.redis.routing.RedisSpecificRoutes

/**
 * Class created on 2/21/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
object SkeletonRoutingManager
{
    fun Application.configureRouting() {
        routing {
            get("/") {
                val uri = call.request.uri
                call.respondText("Request uri: $uri")
            }

            /**
             * Any route that has to do with deploying
             * things through Docker.
             */
            get("/deployment/container/{id}") {
                ContainerSpecificRoutes.containerDataRequest(call)
            }
            get("/deployment/container/{id}/status") {
                ContainerSpecificRoutes.retrieveContainerStatus(call)
            }
            get("/deployment/images/{id}") {
                ImageSpecificRoutes.imageDataRequest(call)
            }
            post("/deployment/images/{id}/launch") {
                ImageSpecificRoutes.launchImageRequest(call)
            }

            /**
             * Any route that has to do with managing
             * Redis entries
             */
            get("/redis/container/dump") {
                RedisSpecificRoutes.dumpRedisRequest(call)
            }
        }
    }
}