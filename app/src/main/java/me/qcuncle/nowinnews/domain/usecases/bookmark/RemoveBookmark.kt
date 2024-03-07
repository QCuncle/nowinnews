package me.qcuncle.nowinnews.domain.usecases.bookmark

import me.qcuncle.nowinnews.data.local.BookmarkDao
import javax.inject.Inject

class RemoveBookmark @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    suspend operator fun invoke(url: String) {
        bookmarkDao.remove(url)
    }
}