package me.qcuncle.nowinnews.domain.usecases.search

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.data.local.BookmarkDao
import me.qcuncle.nowinnews.domain.model.Bookmark
import javax.inject.Inject

class SearchBookmarkByKeyword @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    operator fun invoke(keyword: String): Flow<List<Bookmark>> {
        return bookmarkDao.getBookmarkByKeyword(keyword)
    }
}