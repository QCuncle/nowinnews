package me.qcuncle.nowinnews.data.remote.xpath

import android.util.Log
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteConfig
import org.seimicrawler.xpath.JXDocument

class PositionXpathProcessor : XpathProcessor() {
    override fun analyzingArticles(
        config: SiteConfig,
        document: JXDocument
    ): List<Article> {
        val positions = document.selN(config.articleXpath.position)
        val titleList = document.selN(config.articleXpath.title)
        val popularityList = if (config.articleXpath.popularity.isEmpty()) null
        else document.selN(config.articleXpath.popularity)
        val imgUrlList = if (config.articleXpath.imageUrl.isEmpty()) null
        else document.selN(config.articleXpath.imageUrl)
        val urlList = document.selN(config.articleXpath.url)
        val articles = mutableListOf<Article>()

        var realPosition = 0
        positions.forEach { jxNode ->
            jxNode.asString().toLongOrNull()?.let { pValue ->
                val article = Article(
                    siteId = config.id,
                    siteName = config.name,
                    siteIconUrl = config.siteIconUrl,
                    position = pValue.toInt(),
                    title = titleList[realPosition + 1].asString(),
                    url = if (urlList[realPosition + 1].asString().startsWith("https://"))
                        urlList[realPosition + 1].asString()
                    else if (urlList[realPosition + 1].asString().startsWith("//"))
                        "https:${urlList[realPosition + 1].asString()}"
                    else config.host + urlList[realPosition + 1].asString(),
                    imageUrl = if (config.articleXpath.imageUrl.isEmpty()) ""
                    else if (imgUrlList!![realPosition + 1].asString().startsWith("https://"))
                        imgUrlList[realPosition + 1].asString()
                    else if (imgUrlList!![realPosition + 1].asString().startsWith("//"))
                        "https:${imgUrlList[realPosition + 1].asString()}"
                    else config.host + imgUrlList!![realPosition + 1].asString(),
                    popularity = if (config.articleXpath.popularity.isEmpty()) ""
                    else popularityList!![realPosition].asString(),
                    updateTime = System.currentTimeMillis()
                )
                articles.add(article)
            }
            realPosition++
        }
        Log.d("getArticles", "${config.name}, num:${articles.size}")
        return articles
    }
}