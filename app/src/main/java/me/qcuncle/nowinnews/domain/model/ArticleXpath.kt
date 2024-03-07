package me.qcuncle.nowinnews.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ArticleXpath(
    val position: String,
    val title: String,
    val url: String,
    val popularity: String,
    val imageUrl: String,
    val parameter: String
) : Parcelable



