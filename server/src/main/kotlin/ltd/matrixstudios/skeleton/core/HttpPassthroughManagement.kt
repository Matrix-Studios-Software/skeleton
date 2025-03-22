package ltd.matrixstudios.skeleton.core

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*

/**
 * Class created on 2/21/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
object HttpPassthroughManagement
{
    fun Application.configureHTTP()
    {
        install(DefaultHeaders) {
            header("X-Engine", "Skeleton") // will send this header with each response
        }
        install(CORS) {
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Patch)
            allowHeader(HttpHeaders.Authorization)
            allowMethod(HttpMethod.Post)
            allowHeader("Skeleton-Authorization")
            anyHost()
        }
    }
}