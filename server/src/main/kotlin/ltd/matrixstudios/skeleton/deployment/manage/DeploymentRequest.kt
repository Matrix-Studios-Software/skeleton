package ltd.matrixstudios.skeleton.deployment.manage

data class DeploymentRequest(
    val templateId: String,
    val exposedPort: Int,
    val bindedPort: Int,
    val hostName: String
)