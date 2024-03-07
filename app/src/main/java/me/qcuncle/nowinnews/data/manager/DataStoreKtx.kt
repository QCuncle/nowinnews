package me.qcuncle.nowinnews.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import me.qcuncle.nowinnews.util.Constant

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.APP_SETTING)
val Context.subscriptionDataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.SUBSCRIPTION_CONFIGURATION)

internal object PreferenceKeys {
    // appDataStore PreferenceKeys
    val APP_ENTRY = booleanPreferencesKey(Constant.APP_ENTRY)
    val DARK_MODE_CONFIG = intPreferencesKey(Constant.DARK_MODE_CONFIG)
    val THEME_CONFIG_INDEX = intPreferencesKey(Constant.THEME_CONFIG_INDEX)

    // subscriptionDataStore PreferenceKeys
    val NUMBER_OF_ARTICLES = intPreferencesKey(Constant.NUMBER_OF_ARTICLES)
}
