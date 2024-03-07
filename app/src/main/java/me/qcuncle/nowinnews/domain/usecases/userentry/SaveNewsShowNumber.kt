package me.qcuncle.nowinnews.domain.usecases.userentry

import me.qcuncle.nowinnews.domain.manager.SubscriptionManager
import javax.inject.Inject

class SaveNewsShowNumber @Inject constructor(
    private val subscriptionManager: SubscriptionManager
) {
    suspend operator fun invoke(num: Int) {
        subscriptionManager.setShowNumberOfHotArticle(num)
    }
}