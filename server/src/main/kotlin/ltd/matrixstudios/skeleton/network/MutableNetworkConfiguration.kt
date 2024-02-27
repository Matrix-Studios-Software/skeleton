package ltd.matrixstudios.skeleton.network

abstract class MutableNetworkConfiguration(
    val fileDirectory: String,
)
{
    abstract fun setPortInformation(key: String, port: Int)
}