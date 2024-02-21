package ltd.matrixstudios.skeleton.deployment.scaling

import kotlinx.serialization.Serializable

/**
 * Class created on 2/21/2024

 * @author 98ping
 * @project skeleton
 * @website https://solo.to/redis
 */
@Serializable
data class ReplicationProperties(
    val maximumReplications: Int,
    val minimumReplications: Int,
    val replicationRate: Int // for each time a replication is required. Ex. 1,2,3,4
)