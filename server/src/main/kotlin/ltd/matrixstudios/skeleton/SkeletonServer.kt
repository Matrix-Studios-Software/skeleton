package ltd.matrixstudios.skeleton

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


class SkeletonServer
{
    companion object
    {
        @JvmStatic
        fun main(args: Array<String>)
        {
            embeddedServer(Netty, port = 6969, host = "0.0.0.0", module = Application::module)
                .start(wait = true)
        }
    }
}