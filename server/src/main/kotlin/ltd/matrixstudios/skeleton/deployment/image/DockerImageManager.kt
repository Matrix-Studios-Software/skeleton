package ltd.matrixstudios.skeleton.deployment.image

import com.github.dockerjava.api.command.BuildImageResultCallback
import com.github.dockerjava.api.model.BuildResponseItem
import com.github.dockerjava.api.model.Image
import ltd.matrixstudios.skeleton.deployment.DeploymentService
import ltd.matrixstudios.skeleton.deployment.repository.RepositoryTemplateService
import ltd.matrixstudios.skeleton.formatId
import java.io.File


/**
 * Class created on 2/22/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
object DockerImageManager
{
    fun listImages(): List<Image> = DeploymentService.dockerClient.listImagesCmd().exec()

    fun createImage(dockerFile: File): String
    {
        val callback: BuildImageResultCallback = object : BuildImageResultCallback()
        {
            override fun onNext(item: BuildResponseItem)
            {
                super.onNext(item)
            }
        }

        return DeploymentService.dockerClient.buildImageCmd(dockerFile)
            .exec(callback).awaitImageId()
    }

    fun applyImageTag(id: String, repo: String, tag: String) =
        DeploymentService.dockerClient.tagImageCmd(id, repo, tag).exec()

    fun getImageDataFromSHA(id: String): Image?
    {
        return listImages().firstOrNull { it.id == id.formatId() }
    }

    fun getImageDataFromTag(tag: String): Image? = listImages().firstOrNull { image ->
        image.repoTags.any { it.equals(tag, ignoreCase = true) }
    }

    fun getTemplateBasedOnTag(tag: String) = RepositoryTemplateService
        .templates.values.firstOrNull { target ->
            target.containers.any { id ->
                getImageDataFromSHA(id)?.repoTags?.any { it.equals(tag, ignoreCase = true) } ?: false
            }
        }
}