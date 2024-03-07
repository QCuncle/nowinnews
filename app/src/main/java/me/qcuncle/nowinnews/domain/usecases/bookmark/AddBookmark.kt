package me.qcuncle.nowinnews.domain.usecases.bookmark

import me.qcuncle.nowinnews.data.local.BookmarkDao
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.Bookmark
import javax.inject.Inject

class AddBookmark @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    suspend operator fun invoke(article: Article) {
        val bookmark = Bookmark(
            siteId = article.siteId,
            siteName = article.siteName,
            siteIconUrl = article.siteIconUrl,
            title = article.title,
            url = article.url,
            imageUrl = article.imageUrl,
            popularity = article.popularity,
            collectionTime = System.currentTimeMillis()
        )
        bookmarkDao.insert(bookmark)
    }
}