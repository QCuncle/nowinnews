package me.qcuncle.nowinnews.presentation.search

import me.qcuncle.nowinnews.domain.model.Article


sealed class SearchEvent {
    data class Search(val keyword: String) : SearchEvent()
    data class Subscription(val id: Int) : SearchEvent()
    data class Unsubscribe(val id: Int) : SearchEvent()
    data class Collect(val article: Article) : SearchEvent()
}