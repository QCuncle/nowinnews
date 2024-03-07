package me.qcuncle.nowinnews.presentation.nvgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import me.qcuncle.nowinnews.presentation.navigator.Navigator
import me.qcuncle.nowinnews.presentation.onboarding.OnBoardingScreen
import me.qcuncle.nowinnews.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(viewModel::onEvent)
            }
        }

        navigation(
            route = Route.Navigation.route,
            startDestination = Route.NavigatorScreen.route
        ) {
            composable(route = Route.NavigatorScreen.route){
                Navigator()
            }
        }
    }
}
