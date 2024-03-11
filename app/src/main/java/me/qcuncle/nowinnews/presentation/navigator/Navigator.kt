package me.qcuncle.nowinnews.presentation.navigator

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.presentation.bookmark.BookmarkScreen
import me.qcuncle.nowinnews.presentation.bookmark.BookmarkViewModel
import me.qcuncle.nowinnews.presentation.hot.HotListScreen
import me.qcuncle.nowinnews.presentation.hot.HotViewModel
import me.qcuncle.nowinnews.presentation.navigator.compoments.BottomNavigation
import me.qcuncle.nowinnews.presentation.navigator.compoments.BottomNavigationItem
import me.qcuncle.nowinnews.presentation.navigator.compoments.NinTopBar
import me.qcuncle.nowinnews.presentation.nvgraph.Route
import me.qcuncle.nowinnews.presentation.search.SearchScreen
import me.qcuncle.nowinnews.presentation.search.SearchViewModel
import me.qcuncle.nowinnews.presentation.setting.SettingScreen
import me.qcuncle.nowinnews.presentation.setting.SettingViewModel
import me.qcuncle.nowinnews.presentation.subscription.NodeListScreen
import me.qcuncle.nowinnews.presentation.subscription.NodeViewModel
import me.qcuncle.nowinnews.presentation.viewmore.ViewMoreScreen
import me.qcuncle.nowinnews.presentation.viewmore.ViewMoreViewModel


@Composable
fun Navigator() {
    val titleHotList = stringResource(id = R.string.hot_list)
    val titleBookmark = stringResource(id = R.string.bookmark)
    val titleSetting = stringResource(id = R.string.setting)
    val titleSubscription = stringResource(id = R.string.subscription)


    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(
                unselectedIcon = R.drawable.outline_whatshot_24,
                selectedIcon = R.drawable.baseline_whatshot_24,
                text = titleHotList
            ),
            BottomNavigationItem(
                unselectedIcon = R.drawable.outline_bookmarks_24,
                selectedIcon = R.drawable.baseline_bookmarks_24,
                text = titleBookmark
            ),
            BottomNavigationItem(
                unselectedIcon = R.drawable.outline_settings_24,
                selectedIcon = R.drawable.baseline_settings_24,
                text = titleSetting
            )
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HotListScreen.route -> 0
        Route.BookMarkScreen.route -> 1
        Route.SettingsScreen.route -> 2
        else -> 0
    }

    //Hide the top navigation when the user is in the details screen
    val isTopBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HotListScreen.route ||
                backStackState?.destination?.route == Route.BookMarkScreen.route ||
                backStackState?.destination?.route == Route.SettingsScreen.route ||
                backStackState?.destination?.route == Route.NodeScreen.route
    }

    var isBottomBarVisible by remember { mutableStateOf(true) }

    LaunchedEffect(backStackState) {
        isBottomBarVisible = backStackState?.destination?.route == Route.HotListScreen.route ||
                backStackState?.destination?.route == Route.BookMarkScreen.route ||
                backStackState?.destination?.route == Route.SettingsScreen.route
    }

//    //Hide the bottom navigation when the user is in the details screen
//    var isBottomBarVisible = remember(key1 = backStackState) {
//        backStackState?.destination?.route == Route.HotListScreen.route ||
//                backStackState?.destination?.route == Route.BookMarkScreen.route ||
//                backStackState?.destination?.route == Route.SettingsScreen.route
//    }

    var pageTitle by remember { mutableStateOf(titleHotList) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isTopBarVisible) {
                NinTopBar(
                    title = pageTitle,
                    onSearchClick = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    onNodeClick = {
                        navigateToTab(
                            navController = navController,
                            route = Route.NodeScreen.route
                        )
                    },
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                BottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToNavigationTab(
                                navController = navController,
                                route = Route.HotListScreen.route
                            )

                            1 -> navigateToNavigationTab(
                                navController = navController,
                                route = Route.BookMarkScreen.route
                            )

                            2 -> navigateToNavigationTab(
                                navController = navController,
                                route = Route.SettingsScreen.route
                            )
                        }
                    }
                )
            }
        }) {
        val topPadding = it.calculateTopPadding()
        // val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = Route.HotListScreen.route,
            modifier = Modifier.padding(top = topPadding)
        ) {
            composable(route = Route.HotListScreen.route) {
                pageTitle = titleHotList
                val viewModel: HotViewModel = hiltViewModel()
                val siteEntities = viewModel.hotData.collectAsState(initial = emptyList())
                val isLoading = viewModel.isLoading
                val isEmpty = viewModel.isEmpty
                HotListScreen(
                    isLoading = isLoading.value,
                    isEmpty = isEmpty.value,
                    siteEntities = siteEntities.value,
                    event = viewModel::onEvent,
                    viewMore = { id ->
                        navigateToViewMore(navController, id)
                    },
                    onBottomBarVisible = { visible ->
                        if (visible && !isBottomBarVisible) {
                            isBottomBarVisible = true
                        } else if (!visible && isBottomBarVisible) {
                            isBottomBarVisible = false
                        }
                    }
                )
            }
            composable(route = Route.BookMarkScreen.route) {
                pageTitle = titleBookmark
                val viewModel: BookmarkViewModel = hiltViewModel()
                val bookmarks = viewModel.data.collectAsState(initial = emptyList())
                val isEmpty = viewModel.isEmpty
                BookmarkScreen(
                    bookmarks = bookmarks.value,
                    isEmpty = isEmpty.value,
                    onBottomBarVisible = { visible ->
                        if (visible && !isBottomBarVisible) {
                            isBottomBarVisible = true
                        } else if (!visible && isBottomBarVisible) {
                            isBottomBarVisible = false
                        }
                    },
                    event = viewModel::onEvent
                )
            }
            composable(route = Route.SettingsScreen.route) {
                pageTitle = titleSetting
                val viewModel: SettingViewModel = hiltViewModel()
                val showNum = viewModel.showNum.collectAsState()
                val darkMode = viewModel.darkMode.collectAsState()
                val themeIndex = viewModel.themeIndex.collectAsState()
                SettingScreen(
                    newsShowNum = showNum.value,
                    darkMode = darkMode.value,
                    themeIndex = themeIndex.value,
                    onBottomBarVisible = { visible ->
                        if (visible && !isBottomBarVisible) {
                            isBottomBarVisible = true
                        } else if (!visible && isBottomBarVisible) {
                            isBottomBarVisible = false
                        }
                    },
                    event = viewModel::onEvent
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val articles = viewModel.articles
                val siteConfigs = viewModel.siteConfig
                val bookmarks = viewModel.bookmarks
                val dialogState = viewModel.dialogState
                val isEmpty = viewModel.isEmpty
                SearchScreen(
                    articles = articles.value,
                    siteConfigs = siteConfigs.value,
                    bookmarks = bookmarks.value,
                    dialogState = dialogState.value,
                    isEmpty = isEmpty.value,
                    event = viewModel::onEvent,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
            composable(route = Route.NodeScreen.route) {
                pageTitle = titleSubscription
                val viewModel: NodeViewModel = hiltViewModel()
                val siteConfigs = viewModel.nodeData.collectAsState(initial = emptyList())
                val dialogState = viewModel.dialogState
                NodeListScreen(siteConfigs.value, dialogState.value, viewModel::onEvent)
            }
            composable(route = Route.SiteDetailScreen.route) {
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("id")?.let { id ->
                    // Get the arguments from the NavBackStackEntry
                    val viewModel: ViewMoreViewModel = hiltViewModel()
                    val siteEntity = viewModel.data.collectAsState()
                    viewModel.loadData(id)
                    LaunchedEffect(Unit) {
                        viewModel.loadData(id)
                    }
                    ViewMoreScreen(
                        siteEntity = siteEntity.value,
                        event = viewModel::onEvent
                    )
                }
            }
        }
    }
}

@Composable
private fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HotListScreen.route
        )
    }
}

private fun navigateToNavigationTab(
    navController: NavController,
    route: String,
) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToTab(
    navController: NavController,
    route: String,
) {
    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToViewMore(navController: NavController, id: Int) {
    navController.currentBackStackEntry?.savedStateHandle?.set("id", id)
    navController.navigate(
        route = Route.SiteDetailScreen.route
    )
}