package me.qcuncle.nowinnews.presentation.search


sealed class SearchEvent {
    data class Search(val keyword: String) : SearchEvent()
    data class SubscriptionEvent(val id: Int) : SearchEvent()
    data class UnsubscribeEvent(val id: Int) : SearchEvent()
}