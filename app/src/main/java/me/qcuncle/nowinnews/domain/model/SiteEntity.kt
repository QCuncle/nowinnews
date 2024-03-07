package me.qcuncle.nowinnews.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SiteEntity(
    val id: Int,
    val name: String,
    val siteIcon: String,
    val articles: List<Article>
) : Parcelable