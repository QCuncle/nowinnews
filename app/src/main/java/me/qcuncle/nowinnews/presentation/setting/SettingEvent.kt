package me.qcuncle.nowinnews.presentation.setting

import android.content.Context

sealed class SettingEvent {
    data class SaveShowNum(val num: Int) : SettingEvent()
    data class SaveDarkMode(val darkMode: Int) : SettingEvent()
    data class SaveThemeConfig(val themeIndex: Int) : SettingEvent()
    data object RestSubscriptionConfiguration : SettingEvent()
    data class UploadSubscriptionConfiguration(
        val context: Context, val jsonString: String?
    ) : SettingEvent()

    data class ExportFile(val context: Context) : SettingEvent()
}