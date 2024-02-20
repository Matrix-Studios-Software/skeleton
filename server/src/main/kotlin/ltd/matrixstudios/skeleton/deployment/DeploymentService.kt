package ltd.matrixstudios.skeleton.deployment

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.DockerClientConfig

object DeploymentService
{
    private val dockerConfig: DockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
        .withDockerHost("tcp://localhost:2375")
        .build()

    val dockerClient: DockerClient = DockerClientBuilder.getInstance(dockerConfig).build()


}