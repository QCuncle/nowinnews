package me.qcuncle.nowinnews.domain.usecases.node

import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.domain.model.SiteConfig
import javax.inject.Inject

class InsertSiteConfig @Inject constructor(
    private val siteConfigDao: SiteConfigDao
) {
    suspend operator fun invoke(siteConfigs: List<SiteConfig>) {
        siteConfigDao.insert(siteConfigs)
    }
}