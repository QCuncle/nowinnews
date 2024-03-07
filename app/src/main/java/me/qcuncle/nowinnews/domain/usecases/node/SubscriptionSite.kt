package me.qcuncle.nowinnews.domain.usecases.node

import me.qcuncle.nowinnews.data.local.SiteConfigDao
import javax.inject.Inject

class SubscriptionSite @Inject constructor(
    private val siteConfigDao: SiteConfigDao
) {
    suspend operator fun invoke(id: Int) {
        siteConfigDao.updateSubscriptionConfig(id, true)
    }
}