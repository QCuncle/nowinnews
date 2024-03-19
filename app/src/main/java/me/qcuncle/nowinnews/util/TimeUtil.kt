package me.qcuncle.nowinnews.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

/**
 * Possible format of Time.
 */
enum class TimeFormat(val formatString: String) {
    DEFAULT_FORMAT("yyyy-MM-dd HH:mm:ss"),
    FORMAT0("yyyyMMddHHmmss"),
    FORMAT1("yyyy/MM/dd HH:mm:ss"),
    FORMAT2("yyyy-MM-dd"),
    FORMAT3("yyyy/MM/dd"),
    FORMAT4("HH:mm:ss")
}

/**
 * Convert time (Long type) to String [format]
 */
fun Long.formatTime(format: TimeFormat = TimeFormat.DEFAULT_FORMAT): String {
    val dateFormat = SimpleDateFormat(format.formatString, Locale.getDefault())
    return dateFormat.format(Date(this))
}

/**
 * Convert String [format] to time (Long type)
 */
fun String.parseTime(format: TimeFormat = TimeFormat.DEFAULT_FORMAT): Long {
    val dateFormat = SimpleDateFormat(format.formatString, Locale.getDefault())
    return dateFormat.parse(this)?.time ?: 0
}

fun Long.formatDisplayTime(format: TimeFormat = TimeFormat.DEFAULT_FORMAT): String {
    val now = System.currentTimeMillis()
    val diffMillis = abs(now - this)

    return when {
        diffMillis < 60 * 1000 -> "刚刚"
        diffMillis < 60 * 60 * 1000 -> "${diffMillis / (60 * 1000)}分钟前"
        diffMillis < 24 * 60 * 60 * 1000 -> "${diffMillis / (60 * 60 * 1000)}小时前"
        diffMillis < 48 * 60 * 60 * 1000 -> "昨天"
        else -> this.formatTime(format)
    }
}