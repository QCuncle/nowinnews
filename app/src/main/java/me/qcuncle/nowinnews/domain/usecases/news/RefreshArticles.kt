package me.qcuncle.nowinnews.domain.usecases.news

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.domain.repository.NewsRepository
import javax.inject.Inject

class RefreshArticles @Inject constructor(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(id: Int): Flow<SiteEntity> {
        return newsRepository.refreshArticlesBySiteId(id)
    }
}