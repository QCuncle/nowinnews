package me.qcuncle.nowinnews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.qcuncle.nowinnews.util.CrashHandler

@HiltAndroidApp
class NinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CrashHandler.init(this)
    }
}