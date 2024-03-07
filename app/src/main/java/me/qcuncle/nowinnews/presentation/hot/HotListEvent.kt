package me.qcuncle.nowinnews.presentation.hot

import android.content.Context
import android.graphics.Bitmap

sealed class HotEvent {
    data class ShareCardEvent(
        val context: Context,
        val bitmap: Bitmap?,
        val filePath: String
    ) : HotEvent()
    data class RefreshCardEvent(val siteId: Int) : HotEvent()
    data object RefreshAll : HotEvent()
}