package me.qcuncle.nowinnews.presentation.subscription

sealed class NodeEvent {
    data class SubscriptionEvent(val id: Int) : NodeEvent()
    data class UnsubscribeEvent(val id: Int) : NodeEvent()
}