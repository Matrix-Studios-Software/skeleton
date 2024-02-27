package ltd.matrixstudios.skeleton.network.type

import ltd.matrixstudios.skeleton.network.MutableNetworkConfiguration
import java.io.File
import java.util.*

class PropertiesNetworkConfiguration(fileDirectory: String) : MutableNetworkConfiguration(fileDirectory)
{
    override fun setPortInformation(key: String, port: Int)
    {
        val file = File(this.fileDirectory)
        val properties = Properties()

        if (file.exists() && file.isFile)
        {
            properties.load(file.reader())
            properties.setProperty(key, port.toString())
            properties.store(file.writer(), null)
        }
    }
}