package me.qcuncle.nowinnews.data.remote

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import me.qcuncle.nowinnews.data.remote.xpath.DefaultXpathProcessor
import me.qcuncle.nowinnews.data.remote.xpath.PositionXpathProcessor
import me.qcuncle.nowinnews.data.remote.xpath.TitleXpathProcessor
import me.qcuncle.nowinnews.data.remote.xpath.ZhiHuXpathProcessor
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.model.SiteEntity
import org.apache.commons.lang3.StringEscapeUtils
import org.seimicrawler.xpath.JXDocument
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.resume


class NewsRequestImpl @Inject constructor(
    private val application: Application,
) : NewsApi {
    @Throws
    override suspend fun synchronizeArticles(siteConfig: SiteConfig): SiteEntity? {
        return withContext(Dispatchers.IO) {
            val articles = getArticlesFromConfig(siteConfig)
            if (articles.isNotEmpty()) {
                SiteEntity(
                    id = articles[0].siteId,
                    name = articles[0].siteName,
                    siteIcon = articles[0].siteIconUrl,
                    articles = articles
                )
            } else {
                null
            }
        }
    }

    private suspend fun getArticlesFromConfig(config: SiteConfig): List<Article> {
        return withContext(Dispatchers.IO) {
            val value = getRealHTMLContent(config.siteUrl, config.delay)
            val decodedUnicode = StringEscapeUtils.unescapeJava(value)
            val html = StringEscapeUtils.unescapeHtml3(decodedUnicode)
            val document = JXDocument.create(html)
            when (config.articleXpath.parameter) {
                "position" -> PositionXpathProcessor().analyzingArticles(config, document)
                "title" -> TitleXpathProcessor().analyzingArticles(config, document)
                "zhihu" -> ZhiHuXpathProcessor().analyzingArticles(config, document, html)
                "default" -> DefaultXpathProcessor().analyzingArticles(config, document)
                else -> emptyList()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private suspend fun getRealHTMLContent(url: String, delay: Long): String {
        return withContext(Dispatchers.Main) {
            suspendCancellableCoroutine { continuation ->
                val webView = WeakReference(WebView(application))
                val resultChannel = Channel<String>()

                webView.get()?.apply {
                    Log.d("initWebView", url)
                    settings.apply {
                        //禁用缩放
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
                    webViewClient = object : WebViewClient() {
                        private var isEvaluate = true

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            return false
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                if (isEvaluate) {
                                    isEvaluate = false
                                    view?.evaluateJavascript(
                                        "(function() { return '<html>' + document.getElementsByTagName('html')[0].innerHTML + '</html>'; })();"
                                    ) { value ->
                                        resultChannel.trySend(value)
                                        Log.d("getArticlesHtml", "$url")
                                        continuation.resume(value) // Resume the coroutine when evaluateJavascript is done
                                        webView.clear()
                                    }
                                }
                            }, delay)
                        }
                    }
                    loadUrl(url)
                }
                continuation.invokeOnCancellation {
                    // Handle cancellation if needed
                    resultChannel.close()
                }
            }
        }
    }
}