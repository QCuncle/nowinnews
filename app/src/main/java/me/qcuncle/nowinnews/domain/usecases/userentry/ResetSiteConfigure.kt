package me.qcuncle.nowinnews.domain.usecases.userentry

import me.qcuncle.nowinnews.domain.manager.SubscriptionManager
import javax.inject.Inject

class ResetSiteConfigure @Inject constructor(
    private val subscriptionManager: SubscriptionManager
) {
    suspend operator fun invoke() {
        subscriptionManager.resetSiteConfigs()
    }
}