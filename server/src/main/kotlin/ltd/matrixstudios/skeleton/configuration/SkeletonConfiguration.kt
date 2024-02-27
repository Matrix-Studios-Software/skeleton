package ltd.matrixstudios.skeleton.configuration

import ltd.matrixstudios.amber.configurations.annotate.EntryName
import ltd.matrixstudios.amber.configurations.annotate.Intrinsic
import ltd.matrixstudios.amber.configurations.annotate.primitives.DefaultString

interface SkeletonConfiguration
{
    @Intrinsic
    @EntryName("template-path")
    @DefaultString("JAR_PATH")
    fun getTemplatePath(): String

    @Intrinsic
    @EntryName("network-configuration-path")
    @DefaultString("None")
    fun getNetworkConfigurationPath(): String

    @Intrinsic
    @EntryName("network-configuration-property")
    @DefaultString("None")
    fun getNetworkConfigurationProperty(): String
}