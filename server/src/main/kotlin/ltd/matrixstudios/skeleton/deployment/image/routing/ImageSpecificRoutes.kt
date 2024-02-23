package ltd.matrixstudios.skeleton.deployment.image.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import ltd.matrixstudios.skeleton.GSON
import ltd.matrixstudios.skeleton.deployment.image.DockerImageManager

/**
 * Class created on 2/22/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
object ImageSpecificRoutes
{
    /**
     * Request general data about an image
     *
     * @path /deployment/images/{id}
     */
    suspend fun imageDataRequest(call: ApplicationCall)
    {
        val idParameter = call.parameters["id"]

        if (idParameter == null)
        {
            call.respond(HttpStatusCode(404, "No Id Parameter"))
            return
        } else
        {
            val imageData = DockerImageManager.getImageData(idParameter.lowercase())

            if (imageData == null)
            {
                call.respond(HttpStatusCode(404, "Image does not exist"))
                return
            }

            call.respond(GSON.toJson(imageData))
        }
    }
}