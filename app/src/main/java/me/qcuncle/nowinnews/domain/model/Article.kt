package me.qcuncle.nowinnews.domain.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(primaryKeys = ["position", "siteName"])
data class Article(
    val siteId: Int,
    val siteName: String,
    val siteIconUrl: String,
    val position: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val popularity: String,
    val updateTime: Long
) : Parcelable
