package me.qcuncle.nowinnews.presentation.bookmark

sealed class BookmarkEvent {
    data class DeleteEvent(val url: String) : BookmarkEvent()
}