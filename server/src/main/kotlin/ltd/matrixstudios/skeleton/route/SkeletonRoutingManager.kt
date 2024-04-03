package ltd.matrixstudios.skeleton.route

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ltd.matrixstudios.skeleton.deployment.container.routing.ContainerSpecificRoutes.configureContainerRoutes
import ltd.matrixstudios.skeleton.deployment.image.routing.ImageSpecificRoutes.configureImageRoutes
import ltd.matrixstudios.skeleton.redis.routing.RedisSpecificRoutes
import ltd.matrixstudios.skeleton.redis.routing.RedisSpecificRoutes.configureRedisRoutes

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
            route("/v1") {
                this.configureImageRoutes()
                this.configureContainerRoutes()
                this.configureRedisRoutes()
            }
        }
    }
}