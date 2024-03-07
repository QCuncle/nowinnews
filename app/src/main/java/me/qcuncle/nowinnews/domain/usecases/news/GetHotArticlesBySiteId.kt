package me.qcuncle.nowinnews.domain.usecases.news

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.domain.repository.NewsRepository
import javax.inject.Inject

class GetHotArticlesBySiteId @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(id: Int): Flow<SiteEntity> {
        return newsRepository.getAllHotSubscriptionArticlesById(id)
    }
}