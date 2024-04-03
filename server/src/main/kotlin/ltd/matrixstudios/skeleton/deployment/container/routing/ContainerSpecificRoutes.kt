package ltd.matrixstudios.skeleton.deployment.container.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ltd.matrixstudios.skeleton.GSON
import ltd.matrixstudios.skeleton.deployment.container.DockerContainerManager
import ltd.matrixstudios.skeleton.formatId

/**
 * Class created on 2/21/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
object ContainerSpecificRoutes
{
    fun Route.configureContainerRoutes()
    {
        get("/deployment/container/{id}") {
            containerDataRequest(call)
        }
        get("/deployment/container/{id}/status") {
            retrieveContainerStatus(call)
        }
    }

    /**
     * Request general data about a container
     *
     * @path /deployment/container/{id}
     */
    suspend fun containerDataRequest(call: ApplicationCall)
    {
        val idParameter = call.parameters["id"]

        if (idParameter == null)
        {
            call.respond(HttpStatusCode(404, "No Id Parameter"))
            return
        } else
        {
            val containerData = DockerContainerManager.getContainerData(idParameter.lowercase())

            if (containerData == null)
            {
                call.respond(HttpStatusCode(404, "Container data does not exist"))
                return
            }

            call.respond(GSON.toJson(containerData))
        }
    }

    /**
     * Retrieve the status of a given container
     *
     * @path /deployment/container/{id}/status
     */
    suspend fun retrieveContainerStatus(call: ApplicationCall)
    {
        val idParameter = call.parameters["id"]

        if (idParameter == null)
        {
            call.respond(HttpStatusCode(404, "No Id Parameter"))
            return
        } else
        {
            val containerData = DockerContainerManager.getContainerData(idParameter.lowercase())

            if (containerData == null)
            {
                call.respond(HttpStatusCode(404, "Container data does not exist"))
                return
            }

            call.respondText(containerData.model.status)
        }
    }
}