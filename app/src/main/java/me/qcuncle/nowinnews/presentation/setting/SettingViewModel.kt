package me.qcuncle.nowinnews.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadDarkMode
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadNewsShowNumber
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadThemeConfig
import me.qcuncle.nowinnews.domain.usecases.userentry.ResetSiteConfigure
import me.qcuncle.nowinnews.domain.usecases.userentry.SaveDarkMode
import me.qcuncle.nowinnews.domain.usecases.userentry.SaveNewsShowNumber
import me.qcuncle.nowinnews.domain.usecases.userentry.SaveThemeConfig
import me.qcuncle.nowinnews.util.Constant
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val readNewsShowNumber: ReadNewsShowNumber,
    private val saveNewsShowNumber: SaveNewsShowNumber,
    private val readDarkMode: ReadDarkMode,
    private val saveDarkMode: SaveDarkMode,
    private val readThemeConfig: ReadThemeConfig,
    private val saveThemeConfig: SaveThemeConfig,
    private val resetSiteConfigure: ResetSiteConfigure,
) : ViewModel() {

    private val _showNum = MutableStateFlow(Constant.NUMBER_OF_ARTICLES_DEFAULT)
    val showNum: StateFlow<Int> = _showNum.asStateFlow()

    private val _darkMode = MutableStateFlow(Constant.DARK_MODE_DEFAULT)
    val darkMode: StateFlow<Int> = _darkMode

    private val _themeIndex = MutableStateFlow(Constant.THEME_CONFIG_DEFAULT)
    val themeIndex: StateFlow<Int> = _themeIndex

    init {
        viewModelScope.launch(Dispatchers.IO) {

            launch {
                readNewsShowNumber().collect { num ->
                    _showNum.value = num
                }
            }

            launch {
                readDarkMode().collect { mode ->
                    _darkMode.value = mode
                }
            }

            launch {
                readThemeConfig().collect { config ->
                    _themeIndex.value = config
                }
            }
        }
    }

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.SaveShowNum -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveNewsShowNumber(event.num)
                }
            }

            is SettingEvent.SaveDarkMode -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveDarkMode(event.darkMode)
                }
            }

            is SettingEvent.SaveThemeConfig -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveThemeConfig(event.themeIndex)
                }
            }

            is SettingEvent.RestSubscriptionConfiguration -> {
                viewModelScope.launch {
                    resetSiteConfigure()
                }
            }

        }
    }
}