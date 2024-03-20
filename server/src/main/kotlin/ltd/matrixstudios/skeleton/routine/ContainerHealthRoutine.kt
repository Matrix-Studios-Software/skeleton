package ltd.matrixstudios.skeleton.routine

import ltd.matrixstudios.skeleton.deployment.container.DockerContainerManager
import ltd.matrixstudios.skeleton.sync.ContainerBindingService
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

object ContainerHealthRoutine
{
    fun start()
    {
        val healthThread = thread {
            while (true) {
                val servers = ContainerBindingService.getAllContainerEntries()

                for (entry in servers)
                {
                    val containerState = DockerContainerManager.getContainerInspection(entry.key).ContainerState()

                    if (containerState.running != true)
                    {
                        //TODO: remove server from redis and kill container
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
    }
}