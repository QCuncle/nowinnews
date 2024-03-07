package me.qcuncle.nowinnews.domain.usecases.bookmark

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.data.local.BookmarkDao
import me.qcuncle.nowinnews.domain.model.Bookmark
import javax.inject.Inject

class GetAllBookmarks @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    operator fun invoke(): Flow<List<Bookmark>> {
        return bookmarkDao.getAll()
    }
}