package ltd.matrixstudios.skeleton.configuration

import ltd.matrixstudios.amber.configurations.annotate.EntryName
import ltd.matrixstudios.amber.configurations.annotate.Intrinsic
import ltd.matrixstudios.amber.configurations.annotate.primitives.DefaultString

interface SkeletonConfiguration
{
    @Intrinsic
    @EntryName("template-path")
    @DefaultString("C:\\")
    fun getTemplatePath(): String
}