package ltd.matrixstudios.skeleton.automation

abstract class AutomaticScaleRequirement(
    val id: String,
    val templateId: String
)
{
    abstract fun shouldScale(): Boolean
}