package me.qcuncle.nowinnews.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import me.qcuncle.nowinnews.data.local.NewsDao
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.data.remote.NewsApi
import me.qcuncle.nowinnews.domain.manager.SubscriptionManager
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
    private val siteConfigDao: SiteConfigDao,
    private val subscriptionManager: SubscriptionManager
) : NewsRepository {

    override fun getHotSubscriptionArticles(): Flow<List<SiteEntity>> {
        return flow {
            val siteEntities = mutableListOf<SiteEntity>()
            val displayNum = subscriptionManager.getShowNumberOfHotArticle().first()
            val siteIds = siteConfigDao.getSubscriptionSiteIds()
            val articles = newsDao.getArticles(siteIds, displayNum).first()
            // 根据 siteName 分组 Article 对象
            val groupedArticles: Map<String, List<Article>> = articles
                .groupBy { it.siteName }
            // 打印每个分组中的 Article 对象
            groupedArticles.forEach { (_, articles) ->
                val siteEntity = SiteEntity(
                    id = articles[0].siteId,
                    name = articles[0].siteName,
                    siteIcon = articles[0].siteIconUrl,
                    articles = articles
                )
                siteEntities.add(siteEntity)
            }
            // Sort siteEntities by id
            emit(siteEntities)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAllHotSubscriptionArticlesById(id: Int): Flow<SiteEntity> {
        return flow {
            val articles = newsDao.getArticles(id).first()
            if (articles.isNotEmpty()) {
                val siteEntity = SiteEntity(
                    id = articles[0].siteId,
                    name = articles[0].siteName,
                    siteIcon = articles[0].siteIconUrl,
                    articles = articles
                )
                emit(siteEntity)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun refreshArticlesBySiteId(id: Int): Flow<SiteEntity> {
        return flow {
            val siteConfig = siteConfigDao.getSubscriptionSiteById(id).first()
            val siteEntity = siteConfig?.let {
                newsApi.synchronizeArticles(siteConfig)
            }
            siteEntity?.let {
                newsDao.insert(it.articles)
                val finalEntity = limitArticles(it)
                emit(finalEntity)
            }
        }.flowOn(Dispatchers.IO)
    }

    // Function to limit the number of articles in a SiteEntity to a maximum of 15
    private suspend fun limitArticles(siteEntity: SiteEntity): SiteEntity {
        return withContext(Dispatchers.IO) {
            val maxArticles = subscriptionManager.getShowNumberOfHotArticle().first()
            val limitedArticles = siteEntity.articles.take(maxArticles)
            siteEntity.copy(articles = limitedArticles)
        }
    }

    override fun searchArticles(keyword: String): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}