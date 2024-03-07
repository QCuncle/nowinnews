package me.qcuncle.nowinnews.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.model.SiteEntity

interface NewsRepository {
    /**
     * 获取订阅的热榜站点热门文章
     */
    fun getHotSubscriptionArticles(): Flow<List<SiteEntity>>

    /**
     * 获取指定站点的所有热门文章
     */
    fun getAllHotSubscriptionArticlesById(id: Int): Flow<SiteEntity>

    /**
     * 刷新指定订阅的热门文章
     */
    fun refreshArticlesBySiteId(id: Int): Flow<SiteEntity>

    /**
     * 关键词查询文章
     */
    fun searchArticles(keyword: String): Flow<List<Article>>

}