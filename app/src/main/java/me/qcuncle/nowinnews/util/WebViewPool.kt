package me.qcuncle.nowinnews.util

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object WebViewCachePool {

    private lateinit var application: Application

    private val looper = Looper.getMainLooper()

    private val webViewMap = hashMapOf<String, WebView>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun init(application: Application) {
        this.application = application
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun getOrCreateWebView(url: String, onCreated: (WebView) -> Unit) {
        Log.d("getOrCreateWebView", url)
        return if (webViewMap[url] != null) {
            onCreated(webViewMap[url]!!)
        } else {
            looper.queue.addIdleHandler {
                val webView = WebView(application)
                webView.settings.apply {
                    // 禁用缩放
                    setSupportZoom(false)
                    builtInZoomControls = false
                    displayZoomControls = false
                    loadsImagesAutomatically = false
                    mediaPlaybackRequiresUserGesture = false
                    mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
                    cacheMode = WebSettings.LOAD_DEFAULT
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    userAgentString =
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36 Edg/121.0.0.0"
                }

                webView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return false
                    }
                }
                webViewMap[url] = webView
                onCreated(webView)
                false
            }
        }
    }

    suspend fun loadAndRetrieveHtmlContent(url: String, delay: Long): String {
        var resumed = false // 添加一个标志来确保 resume 只被调用一次
        return withContext(Dispatchers.IO) {
            suspendCoroutine { continuation ->
                getOrCreateWebView(url) { webView ->
                    coroutineScope.launch {
                        webView.apply {
                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    coroutineScope.launch {
                                        delay(delay)
                                        webView.evaluateJavascript(
                                            "(function() { return '<html>' + document.getElementsByTagName('html')[0].innerHTML + '</html>'; })();"
                                        ) { value ->
                                            if (!resumed) {
                                                resumed = true
                                                continuation.resume(value)
                                                webView.stopLoading()
                                            }
                                        }
                                    }
                                }
                            }

                            getUrl()?.let {
                                reload()
                            } ?: run {
                                loadUrl(url)
                            }
                        }
                    }
                }
            }
        }
    }
}
