package ltd.matrixstudios.skeleton.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
                call.respondText("Hello World!")
            }
        }
    }
}