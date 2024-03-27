package ltd.matrixstudios.skeleton.automation.routine

import ltd.matrixstudios.skeleton.automation.AutomaticScaleService
import ltd.matrixstudios.skeleton.deployment.image.DockerImageManager
import ltd.matrixstudios.skeleton.deployment.manage.DeploymentRequest
import ltd.matrixstudios.skeleton.deployment.repository.RepositoryTemplateService
import ltd.matrixstudios.skeleton.log
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

object AutomaticScaleRoutine
{
    lateinit var scaleThread: Thread
    fun startScaling()
    {
        scaleThread = thread {
            while (true)
            {
                for (requirement in AutomaticScaleService.requirements.values)
                {
                    if (requirement.shouldScale())
                    {
                        val template = RepositoryTemplateService.templates.values.firstOrNull {
                            it.id.equals(requirement.templateId, ignoreCase = true)
                        } ?: continue

                        val request = DeploymentRequest(
                            template.id,
                            25565,
                            ThreadLocalRandom.current().nextInt(25565, 25599),
                            "0.0.0.0"
                        )

                        val imageData = DockerImageManager.getImageDataBasedOnTag("${template.id}:1.0") ?: continue

                        AutomaticScaleService.scale(
                            template.replicationProperties?.replicationRate ?: 1,
                            imageData.id,
                            request
                        )

                        log("[Scale Service] Scaling out the template ${template.id} by ${template.replicationProperties?.replicationRate ?: 1}x")
                    }
                }

                try
                {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(15L))
                } catch (e: InterruptedException)
                {
                    e.printStackTrace()
                    return@thread
                }
            }
        }

        scaleThread.name = "Skeleton - Scale Thread"
    }
}