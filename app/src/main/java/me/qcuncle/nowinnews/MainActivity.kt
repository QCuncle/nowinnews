package me.qcuncle.nowinnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import me.qcuncle.nowinnews.presentation.nvgraph.NavGraph
import me.qcuncle.nowinnews.ui.theme.NinTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = {
                viewModel.splashCondition.value
            })
        }
        setContent {
            val darkModeConfig = viewModel.darkMode.collectAsState()
            val themeConfig = viewModel.themeIndex.collectAsState()
            val darkTheme = when (darkModeConfig.value) {
                0 -> true
                1 -> false
                2 -> isSystemInDarkTheme()
                else -> isSystemInDarkTheme()
            }

            NinTheme(
                darkTheme = darkTheme,
                themeIndex = themeConfig.value
            ) {
                NinApp(viewModel)
            }
        }
    }
}

@Composable
fun NinApp(viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val startDestination = viewModel.startDestination
        NavGraph(startDestination = startDestination.value)
    }
}
