package ltd.matrixstudios.skeleton.paths

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ltd.matrixstudios.skeleton.deployment.container.routing.ContainerSpecificRoutes

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

            get("/deployment/container/{id}") {
                ContainerSpecificRoutes.containerDataRequest(call)
            }
        }
    }
}