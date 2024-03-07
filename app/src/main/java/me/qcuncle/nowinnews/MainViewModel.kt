package me.qcuncle.nowinnews

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.domain.usecases.news.RefreshArticles
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadAppEntry
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadDarkMode
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadThemeConfig
import me.qcuncle.nowinnews.domain.usecases.userentry.ResetSiteConfig
import me.qcuncle.nowinnews.presentation.nvgraph.Route
import me.qcuncle.nowinnews.util.Constant
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val readAppEntry: ReadAppEntry,
    private val resetSiteConfig: ResetSiteConfig,
    private val readDarkMode: ReadDarkMode,
    private val readThemeConfig: ReadThemeConfig,
) : ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    private val _darkMode = MutableStateFlow(Constant.DARK_MODE_DEFAULT)
    val darkMode: StateFlow<Int> = _darkMode

    private val _themeIndex = MutableStateFlow(Constant.THEME_CONFIG_DEFAULT)
    val themeIndex: StateFlow<Int> = _themeIndex

    init {
        readAppEntry().onEach { shouldStartFromHomeScreen ->
            if (shouldStartFromHomeScreen) {
                _startDestination.value = Route.Navigation.route
            } else {
                resetSiteConfig()
                _startDestination.value = Route.AppStartNavigation.route
            }
            delay(300) //Without this delay, the onBoarding screen will show for a momentum.
            _splashCondition.value = false
        }.launchIn(viewModelScope)

        readDarkMode().onEach { darkModeConfig ->
            _darkMode.value = darkModeConfig
        }.launchIn(viewModelScope)

        readThemeConfig().onEach { themeConfig ->
            _themeIndex.value = themeConfig
        }.launchIn(viewModelScope)
    }
}