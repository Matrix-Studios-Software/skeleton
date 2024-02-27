package ltd.matrixstudios.skeleton.deployment.manage

data class DeploymentRequest(
    val templateId: String,
    val exposedPort: Int,
    var bindedPort: Int,
    val hostName: String,
    val portProtocol: PortProtocol? = null
)
{
    enum class PortProtocol {
        Static, Randomized
    }
}