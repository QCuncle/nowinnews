package me.qcuncle.nowinnews.data.manager

import android.app.Application
import android.util.Log
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.domain.manager.SubscriptionManager
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.util.AssetsUtil
import me.qcuncle.nowinnews.util.Constant
import javax.inject.Inject

class SubscriptionManagerImpl @Inject constructor(
    private val application: Application,
    private val siteConfigDao: SiteConfigDao
) : SubscriptionManager {
    override suspend fun setShowNumberOfHotArticle(num: Int) {
        application.subscriptionDataStore.edit { settings ->
            settings[PreferenceKeys.NUMBER_OF_ARTICLES] = num
        }
    }

    override fun getShowNumberOfHotArticle(): Flow<Int> {
        return application.subscriptionDataStore.data.map { preferences ->
            preferences[PreferenceKeys.NUMBER_OF_ARTICLES] ?: Constant.NUMBER_OF_ARTICLES_DEFAULT
        }
    }

    override suspend fun configureSiteInfo(jsonString: String): Boolean {
        return try {
            val sites: List<SiteConfig> = Json.decodeFromString<List<SiteConfig>>(jsonString)
            siteConfigDao.insert(sites)
            sites.isNotEmpty()
        } catch (e: Exception) {
            Log.e("configureSiteInfo", e.stackTraceToString())
            false
        }
    }

    override suspend fun resetSiteConfigs() {
        withContext(Dispatchers.IO) {
            siteConfigDao.deleteAll()
            val jsonString = AssetsUtil.readJsonFromAsset("XpathConfig.json")
            configureSiteInfo(jsonString)
        }
    }
}
