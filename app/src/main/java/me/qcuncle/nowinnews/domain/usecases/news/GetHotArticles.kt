package me.qcuncle.nowinnews.domain.usecases.news

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.domain.repository.NewsRepository
import javax.inject.Inject

class GetHotArticles @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<SiteEntity>> {
        return newsRepository.getHotSubscriptionArticles()
    }
}