@file:Suppress("DEPRECATION")

package me.qcuncle.nowinnews.util

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import me.qcuncle.nowinnews.MainActivity
import kotlin.system.exitProcess

// 扩展函数，获取应用版本号
fun Context.getAppVersionCode(): Long {
    return try {
        val packageInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        // 处理异常，返回默认值或采取其他措施
        -1L
    }
}

// 扩展函数，获取应用版本名
fun Context.getAppVersionName(): String {
    return try {
        val packageInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        // 处理异常，返回默认值或采取其他措施
        ""
    }
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

fun Context.rebootApp() {
    this.startActivity(
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    )
    if (this is Activity) {
        this.finish()
    }
}
