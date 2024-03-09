package me.qcuncle.nowinnews.presentation.subscription

import me.qcuncle.nowinnews.domain.model.SiteConfig

sealed class NodeEvent {
    data class SubscriptionEvent(val id: Int) : NodeEvent()
    data class UnsubscribeEvent(val id: Int) : NodeEvent()

    data class ToppingEvent(val siteConfig: SiteConfig) : NodeEvent()
}