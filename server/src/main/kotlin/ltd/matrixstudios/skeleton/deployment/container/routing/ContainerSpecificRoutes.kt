package ltd.matrixstudios.skeleton.deployment.container.routing

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.encodeToString
import ltd.matrixstudios.skeleton.deployment.container.DockerContainerManager
import ltd.matrixstudios.skeleton.deployment.container.wrapper.ContainerData

/**
 * Class created on 2/21/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
object ContainerSpecificRoutes
{
    suspend fun containerDataRequest(call: ApplicationCall)
    {
        val idParameter = call.parameters["id"]

        if (idParameter == null)
        {
            call.respond(HttpStatusCode(502, "No Id Parameter"))
            return
        } else
        {
            val containerData = DockerContainerManager.getContainerData(idParameter.lowercase())

            if (containerData == null)
            {
                call.respond(HttpStatusCode(502, "Container data does not exist"))
                return
            }

            call.respond<String>(DefaultJson.encodeToString<ContainerData>(containerData))
        }
    }
}