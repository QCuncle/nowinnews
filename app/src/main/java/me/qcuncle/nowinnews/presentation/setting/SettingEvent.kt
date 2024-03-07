package me.qcuncle.nowinnews.presentation.setting

sealed class SettingEvent {
    data class SaveShowNum(val num: Int) : SettingEvent()
    data class SaveDarkMode(val darkMode: Int) : SettingEvent()
    data class SaveThemeConfig(val themeIndex: Int) : SettingEvent()
    data object RestSubscriptionConfiguration : SettingEvent()
}