
package cn.kurisu.network

import cn.kurisu.network.model.NetworkChangeList
import cn.kurisu.network.model.NetworkNewsResource
import cn.kurisu.network.model.NetworkTopic


/**
 * Interface representing network calls to the NIA backend
 */
interface NiaNetworkDataSource {
    suspend fun getTopics(ids: List<String>? = null): List<NetworkTopic>

    suspend fun getNewsResources(ids: List<String>? = null): List<NetworkNewsResource>

    suspend fun getTopicChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getNewsResourceChangeList(after: Int? = null): List<NetworkChangeList>
}
