package me.qcuncle.nowinnews.util

import android.app.Application
import android.content.Intent
import android.os.Process
import android.util.Log
import me.qcuncle.nowinnews.CrashInfoActivity
import kotlin.system.exitProcess

object CrashHandler : Thread.UncaughtExceptionHandler {
    private lateinit var application: Application
    fun init(application: Application) {
        CrashHandler.application = application
        Thread.setDefaultUncaughtExceptionHandler(this@CrashHandler)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e("CrashHandler", e.stackTraceToString())
        Intent(application, CrashInfoActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra(CrashInfoActivity.EXTRA_ERROR_MESSAGE, e.message)
            putExtra(CrashInfoActivity.EXTRA_ERROR_STACK, e.stackTraceToString())
            application.startActivity(this)
        }
        Process.killProcess(Process.myPid())
        exitProcess(1)
    }
}