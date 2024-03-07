package me.qcuncle.nowinnews.util

object Constant {
    // spFileName
    const val APP_SETTING = "appSetting"
    const val USER_SETTING = "userSetting"
    const val SUBSCRIPTION_CONFIGURATION = "subscriptionConfiguration"

    // spKeyName
    const val APP_ENTRY = "appEntry"
    const val NUMBER_OF_ARTICLES = "numberOfArticles"
    const val DARK_MODE_CONFIG = "darkModeConfig"
    const val THEME_CONFIG_INDEX = "themeConfigIndex"

    // spDefaultValue
    const val NUMBER_OF_ARTICLES_DEFAULT = 15
    const val THEME_CONFIG_DEFAULT = 3
    const val DARK_MODE_DEFAULT = 2
}

object NewsRequestParam {
    val headers = mapOf<String, String>(
        "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36 Edg/121.0.0.0"
    )
}