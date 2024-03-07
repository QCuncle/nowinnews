package me.qcuncle.nowinnews.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes

fun Context?.showToast(msg: String?, gravity: Int = Gravity.BOTTOM) {
    if (this == null) return
    Toast.makeText(this.applicationContext, msg ?: "null", Toast.LENGTH_SHORT).apply {
        setGravity(gravity, 0, 0)
        show()
    }
}

fun Context?.showToastLong(msg: String?, gravity: Int = Gravity.BOTTOM) {
    if (this == null) return
    Toast.makeText(this.applicationContext, msg ?: "null", Toast.LENGTH_LONG).apply {
        setGravity(gravity, 0, 0)
        show()
    }
}

fun Context?.showToast(@StringRes stringResId: Int, gravity: Int = Gravity.BOTTOM) {
    if (this == null) return
    Toast.makeText(this.applicationContext, this.getString(stringResId), Toast.LENGTH_SHORT).apply {
        setGravity(gravity, 0, 0)
        show()
    }
}

fun Context?.showToastLong(@StringRes stringResId: Int, gravity: Int = Gravity.BOTTOM) {
    if (this == null) return
    Toast.makeText(this.applicationContext, this.getString(stringResId), Toast.LENGTH_LONG).apply {
        setGravity(gravity, 0, 0)
        show()
    }
}