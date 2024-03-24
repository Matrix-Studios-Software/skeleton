package ltd.matrixstudios.skeleton.routine

import ltd.matrixstudios.skeleton.deployment.container.DockerContainerManager
import ltd.matrixstudios.skeleton.log
import ltd.matrixstudios.skeleton.sync.ContainerBindingService
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

object ContainerHealthRoutine
{
    private lateinit var healthThread: Thread

    fun start()
    {
        healthThread = thread {
            while (true) {
                log("[Health] Checking all containers for a health report.")
                val servers = ContainerBindingService.getAllContainerEntries()

                for (entry in servers)
                {
                    val containerState = DockerContainerManager.getContainerInspection(entry.key).ContainerState()

                    if (containerState.running != true)
                    {
                        ContainerBindingService.deleteContainerId(entry.key)
                        DockerContainerManager.deleteContainer(entry.key)

                        log("[Health] Removed container ${entry.key} from Redis and the active Docker container pool.")
                    }
                }

                try
                {
                    sleep(TimeUnit.SECONDS.toMillis(30L))
                } catch (e: InterruptedException)
                {
                    e.printStackTrace()
                    return@thread
                }
            }
        }

        healthThread.name = "Skeleton - Health Thread"
    }
}