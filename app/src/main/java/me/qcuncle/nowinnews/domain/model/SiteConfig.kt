package me.qcuncle.nowinnews.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity
data class SiteConfig(
    @PrimaryKey val id: Int,
    var sort: Int,
    val name: String,
    val host: String,
    val isSubscribed: Boolean,
    val delay: Long,
    val siteUrl: String,
    val siteIconUrl: String,
    val articleXpath: ArticleXpath
) : Parcelable