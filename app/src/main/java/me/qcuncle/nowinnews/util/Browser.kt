package me.qcuncle.nowinnews.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.jumpToBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    this.startActivity(intent)
}