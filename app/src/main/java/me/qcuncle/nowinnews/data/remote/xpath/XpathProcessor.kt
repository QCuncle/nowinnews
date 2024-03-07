package me.qcuncle.nowinnews.data.remote.xpath

import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteConfig
import org.seimicrawler.xpath.JXDocument

abstract class XpathProcessor {
    abstract fun analyzingArticles(config: SiteConfig, document: JXDocument): List<Article>
}