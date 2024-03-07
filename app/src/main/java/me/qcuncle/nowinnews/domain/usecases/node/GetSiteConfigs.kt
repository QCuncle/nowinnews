package me.qcuncle.nowinnews.domain.usecases.node

import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.domain.model.SiteConfig
import javax.inject.Inject

class GetSiteConfigs @Inject constructor(
    private val siteConfigDao: SiteConfigDao
) {
    operator fun invoke(): Flow<List<SiteConfig>> {
        return siteConfigDao.getAllSiteConfigurations()
    }
}