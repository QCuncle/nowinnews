package me.qcuncle.nowinnews.domain.usecases.userentry

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.domain.manager.SubscriptionManager
import javax.inject.Inject

class ReadNewsShowNumber @Inject constructor(
    private val subscriptionManager: SubscriptionManager
) {
    operator fun invoke(): Flow<Int> {
        return subscriptionManager.getShowNumberOfHotArticle()
    }
}