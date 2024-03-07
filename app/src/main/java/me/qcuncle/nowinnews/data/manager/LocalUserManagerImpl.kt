package me.qcuncle.nowinnews.data.manager

import android.app.Application
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.qcuncle.nowinnews.domain.manager.LocalUserManager
import me.qcuncle.nowinnews.util.Constant
import javax.inject.Inject

class LocalUserManagerImpl @Inject constructor(
    private val application: Application
) : LocalUserManager {

    override suspend fun saveAppEntry() {
        application.appDataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return application.appDataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] ?: false
        }
    }

    override suspend fun saveThemeConfig(index: Int) {
        application.appDataStore.edit { settings ->
            settings[PreferenceKeys.THEME_CONFIG_INDEX] = index
        }
    }

    override fun readThemeColor(): Flow<Int> {
        return application.appDataStore.data.map { preferences ->
            preferences[PreferenceKeys.THEME_CONFIG_INDEX] ?: Constant.THEME_CONFIG_DEFAULT
        }
    }

    override suspend fun saveDarkMode(value: Int) {
        application.appDataStore.edit { settings ->
            settings[PreferenceKeys.DARK_MODE_CONFIG] = value
        }
    }

    override fun readDarkMode(): Flow<Int> {
        return application.appDataStore.data.map { preferences ->
            preferences[PreferenceKeys.DARK_MODE_CONFIG] ?: Constant.DARK_MODE_DEFAULT
        }
    }
}
