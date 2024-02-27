package ltd.matrixstudios.skeleton.deployment.image.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ltd.matrixstudios.skeleton.GSON
import ltd.matrixstudios.skeleton.deployment.image.DockerImageManager
import ltd.matrixstudios.skeleton.deployment.manage.DeploymentLogicService
import ltd.matrixstudios.skeleton.deployment.manage.DeploymentRequest
import ltd.matrixstudios.skeleton.formatId
import java.util.concurrent.ThreadLocalRandom

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

    /**
     * Builds and runs and image
     *
     * @path /deployment/images/{id}/launch
     */
    suspend fun launchImageRequest(call: ApplicationCall)
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

            val body = call.receiveText()
            val requestParam = GSON.fromJson(body, DeploymentRequest::class.java)

            if (requestParam == null)
            {
                call.respond(HttpStatusCode(404, "Unable to parse the deployment request"))
                return
            }

            if (requestParam.portProtocol != null && requestParam.portProtocol == DeploymentRequest.PortProtocol.Randomized)
            {
                requestParam.bindedPort = ThreadLocalRandom.current().nextInt(25566, 25599)
            }

            DeploymentLogicService.launchWithDeploymentRequest(idParameter.formatId(), requestParam)
            call.respondText("Attempting to launch this container...")
        }
    }
}