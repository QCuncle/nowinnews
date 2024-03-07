package me.qcuncle.nowinnews.data.remote.xpath

import android.util.Base64
import android.util.Log
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteConfig
import org.seimicrawler.xpath.JXDocument

class ZhiHuXpathProcessor {

    private val numRegex = Regex("[^0-9]")
    private val urlRegex = Regex("\"attached_info_bytes\":\"(.*?)\"")

    fun analyzingArticles(config: SiteConfig, document: JXDocument, html: String): List<Article> {
        val titleList = document.selN(config.articleXpath.title)
        val popularityList = if (config.articleXpath.popularity.isEmpty()) null
        else document.selN(config.articleXpath.popularity)
        val imgUrlList = if (config.articleXpath.imageUrl.isEmpty()) null
        else document.selN(config.articleXpath.imageUrl)

        val matches = urlRegex.findAll(html)
        val urlList = matches.map { matchResult ->
            val base64Value = matchResult.groupValues[1].substring(52, 66)
            val decodedBytes = Base64.decode(base64Value, Base64.DEFAULT)
            val articleId = String(decodedBytes, Charsets.UTF_8).removeNonDigits()
            "${config.host}/${articleId}?utm_division=hot_list_page"
        }.toList()
        val articles = mutableListOf<Article>()
        titleList.forEachIndexed { i, _ ->
            val article = Article(
                siteId = config.id,
                siteName = config.name,
                siteIconUrl = config.siteIconUrl,
                position = i + 1,
                title = titleList[i].asString(),
                url = urlList[i],
                imageUrl = if (config.articleXpath.imageUrl.isEmpty()) ""
                else if (imgUrlList!![i].asString().startsWith("https://")) imgUrlList[i].asString()
                else if (imgUrlList!![i].asString()
                        .startsWith("//")
                ) "https:${imgUrlList[i].asString()}"
                else config.host + imgUrlList!![i].asString(),
                popularity = if (config.articleXpath.popularity.isEmpty()) ""
                else popularityList!![i].asString(),
                updateTime = System.currentTimeMillis()
            )
            articles.add(article)
        }
        Log.d("getArticles", "${config.name}, num:${articles.size}")
        return articles
    }

    private fun String.removeNonDigits(): String = this.replace(numRegex, "")
}