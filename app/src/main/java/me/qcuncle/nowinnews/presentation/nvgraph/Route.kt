package me.qcuncle.nowinnews.presentation.nvgraph

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    /**
     * 首次安装提示页
     */
    data object OnBoardingScreen : Route("onBoardingScreen")

    /**
     * 所有订阅热榜列表页
     */
    data object HotListScreen : Route("hotListScreen")

    /**
     * 搜索
     */
    data object SearchScreen : Route("searchScreen")

    data object Navigation : Route("navigation")

    data object NavigatorScreen : Route("newsNavigatorScreen")

    /**
     * 书签
     */
    data object BookMarkScreen : Route("bookMarkScreen")

    /**
     * 站点节点配置
     */
    data object NodeScreen : Route("nodeScreen")

    /**
     * 设置
     */
    data object SettingsScreen : Route("settingsScreen")

    /**
     * 启动页
     */
    data object AppStartNavigation : Route("appStartNavigation")

    /**
     * 站点全部热榜页
     */
    data object SiteDetailScreen : Route("siteDetailScreen")
}