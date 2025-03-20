package ltd.matrixstudios.skeleton.deployment

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.okhttp.OkDockerHttpClient
import com.github.dockerjava.transport.DockerHttpClient
import ltd.matrixstudios.skeleton.configuration.SkeletonConfigurationService


object DeploymentService
{
    private val dockerConfig: DockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
        .withDockerHost("unix:///var/run/docker.sock")
        .withDockerTlsVerify(true)
        .withDockerCertPath(SkeletonConfigurationService.config.getDockerCertPath())
        .build()

    private val httpClient: DockerHttpClient = OkDockerHttpClient.Builder()
        .dockerHost(dockerConfig.dockerHost)
        .sslConfig(dockerConfig.sslConfig)
        .connectTimeout(30)
        .build()

    val dockerClient: DockerClient = DockerClientImpl.getInstance(dockerConfig, httpClient)
}