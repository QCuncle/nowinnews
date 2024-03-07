package me.qcuncle.nowinnews.data.remote.xpath

import android.util.Log
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteConfig
import org.seimicrawler.xpath.JXDocument

class TitleXpathProcessor : XpathProcessor() {
    override fun analyzingArticles(
        config: SiteConfig,
        document: JXDocument
    ): List<Article> {
        val titleList = document.selN(config.articleXpath.title)
        val popularityList = if (config.articleXpath.popularity.isEmpty()) null
        else document.selN(config.articleXpath.popularity)
        val imgUrlList = if (config.articleXpath.imageUrl.isEmpty()) null
        else document.selN(config.articleXpath.imageUrl)
        val urlList = document.selN(config.articleXpath.url)
        val articles = mutableListOf<Article>()
        var popularityIndex = 0
        titleList.forEachIndexed { i, _ ->
            if (i == 0) return@forEachIndexed
            val article = Article(
                siteId = config.id,
                siteName = config.name,
                siteIconUrl = config.siteIconUrl,
                position = i,
                title = titleList[i].asString(),
                url = if (urlList[i].asString().startsWith("https://")) urlList[i].asString()
                else if (urlList[i].asString().startsWith("//")) "https:${urlList[i].asString()}"
                else config.host + urlList[i].asString(),
                imageUrl = if (config.articleXpath.imageUrl.isEmpty()) ""
                else if (imgUrlList!![i].asString().startsWith("https://")) imgUrlList[i].asString()
                else if (imgUrlList!![i].asString().startsWith("//")) "https:${imgUrlList[i].asString()}"
                else config.host + imgUrlList!![i].asString(),
                popularity = if (config.articleXpath.popularity.isEmpty()) ""
                else popularityList!![popularityIndex].asString(),
                updateTime = System.currentTimeMillis()
            )
            articles.add(article)
            popularityIndex++
        }
        Log.d("getArticles", "${config.name}, num:${articles.size}")
        return articles
    }
}