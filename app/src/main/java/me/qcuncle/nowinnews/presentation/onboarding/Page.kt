package me.qcuncle.nowinnews.presentation.onboarding

import androidx.annotation.DrawableRes
import me.qcuncle.nowinnews.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "热榜",
        description = "热门站点一扫而和",
        image = R.drawable.onboarding_list,
    ),
    Page(
        title = "分享和收藏",
        description = "轻触收藏，分享新鲜事件",
        image = R.drawable.onboarding_save,
    ),
    Page(
        title = "订阅",
        description = "订阅热门站点，搜罗新闻焦点",
        image = R.drawable.onboarding_search,
    ),
)