package me.qcuncle.nowinnews.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.qcuncle.nowinnews.data.remote.xpath.DefaultXpathProcessor
import me.qcuncle.nowinnews.data.remote.xpath.PositionXpathProcessor
import me.qcuncle.nowinnews.data.remote.xpath.TitleXpathProcessor
import me.qcuncle.nowinnews.data.remote.xpath.ZhiHuXpathProcessor
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.util.WebViewCachePool
import org.apache.commons.lang3.StringEscapeUtils
import org.seimicrawler.xpath.JXDocument
import javax.inject.Inject


class NewsRequestImpl @Inject constructor() : NewsApi {
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

    @Suppress("DEPRECATION")
    private suspend fun getArticlesFromConfig(config: SiteConfig): List<Article> {
        return withContext(Dispatchers.IO) {
            val value = WebViewCachePool.loadAndRetrieveHtmlContent(config.siteUrl, config.delay)
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
}