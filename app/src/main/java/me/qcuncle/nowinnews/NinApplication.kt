package me.qcuncle.nowinnews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.qcuncle.nowinnews.util.CrashHandler
import me.qcuncle.nowinnews.util.WebViewCachePool

@HiltAndroidApp
class NinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CrashHandler.init(this)
        WebViewCachePool.init(this)
    }
}