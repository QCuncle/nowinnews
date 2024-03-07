package me.qcuncle.nowinnews.domain.manager

import kotlinx.coroutines.flow.Flow

interface SubscriptionManager {

    /**
     * 保存热榜展示数目
     */
    suspend fun setShowNumberOfHotArticle(num: Int)

    /**
     * 读取热搜展示数目
     */
    fun getShowNumberOfHotArticle(): Flow<Int>

    /**
     * 配置站点信息
     */
    suspend fun configureSiteInfo(jsonString: String): Boolean

    /**
     * 重置站点信息
     */
    suspend fun resetSiteConfigs()
}