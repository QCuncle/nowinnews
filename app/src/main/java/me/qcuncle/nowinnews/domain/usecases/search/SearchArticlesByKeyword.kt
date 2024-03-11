package me.qcuncle.nowinnews.domain.usecases.search

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.repository.NewsRepository
import javax.inject.Inject

class SearchArticlesByKeyword @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(keyword: String): Flow<List<Article>> {
        return newsRepository.searchArticles(keyword)
    }
}