package me.qcuncle.nowinnews.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Bookmark(
    val siteId: Int,
    val siteName: String,
    val siteIconUrl: String,
    val title: String,
    @PrimaryKey val url: String,
    val imageUrl: String,
    val popularity: String,
    val collectionTime: Long
) : Parcelable