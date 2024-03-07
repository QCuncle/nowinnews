package me.qcuncle.nowinnews.data.remote

import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.model.SiteEntity

interface NewsApi {
    /**
     * 获取网络文章
     * @param sites 站点配置表
     */
    suspend fun synchronizeArticles(siteConfig: SiteConfig): SiteEntity?
}