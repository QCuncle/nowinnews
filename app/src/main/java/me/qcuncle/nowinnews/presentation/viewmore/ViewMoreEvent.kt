package me.qcuncle.nowinnews.presentation.viewmore

import me.qcuncle.nowinnews.domain.model.Article

sealed class ViewMoreEvent {
    data class AddBookmark(val article: Article) : ViewMoreEvent()
}