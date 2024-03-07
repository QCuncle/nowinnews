package me.qcuncle.nowinnews.domain.usecases.userentry

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.domain.model.SiteConfig
import javax.inject.Inject

class GetSubscriptionSiteConfig @Inject constructor(
    private val siteConfigDao: SiteConfigDao
) {
    operator fun invoke(): Flow<List<SiteConfig>> {
        return siteConfigDao.getSubscriptionSiteConfigurations()
    }
}