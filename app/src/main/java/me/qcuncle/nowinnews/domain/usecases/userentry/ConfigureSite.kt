package me.qcuncle.nowinnews.domain.usecases.userentry

import me.qcuncle.nowinnews.domain.manager.SubscriptionManager
import javax.inject.Inject

class ConfigureSite @Inject constructor(
    private val subscriptionManager: SubscriptionManager
) {
    suspend operator fun invoke(jsonString: String): Boolean {
        return subscriptionManager.configureSiteInfo(jsonString)
    }
}