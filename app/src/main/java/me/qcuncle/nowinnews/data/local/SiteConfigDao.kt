package me.qcuncle.nowinnews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.domain.model.SiteConfig

@Dao
interface SiteConfigDao {
    /**
     * 配置站点信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sites: List<SiteConfig>)

    /**
     * 查询所有站点信息
     */
    @Query("SELECT * FROM SiteConfig ORDER BY sort ASC")
    fun getAllSiteConfigurations(): Flow<List<SiteConfig>>

    /**
     * 删除所有站点信息
     */
    @Query("DELETE FROM siteconfig")
    suspend fun deleteAll()

    /**
     * 更新站点订阅
     * @param id 站点id
     * @param isSubscribed true-订阅 false-取消订阅
     */
    @Query("UPDATE siteconfig SET isSubscribed = :isSubscribed WHERE id = :id")
    suspend fun updateSubscriptionConfig(id: Int, isSubscribed: Boolean)

    /**
     * 查询订阅的配置
     */
    @Query("SELECT * FROM SiteConfig WHERE isSubscribed = 1 ORDER BY sort ASC")
    fun getSubscriptionSiteConfigurations(): Flow<List<SiteConfig>>

    /**
     * 查询订阅的站点id集合
     */
    @Query("SELECT id FROM SiteConfig WHERE isSubscribed = 1")
    fun getSubscriptionSiteIds(): List<Int>

    /**
     * 根据 ID 查询配置
     */
    @Query("SELECT * FROM SiteConfig WHERE id = :id")
    fun getSubscriptionSiteById(id: Int): Flow<SiteConfig?>

    /**
     * 根据网站名称查询配置信息
     */
    @Query("SELECT * FROM SITECONFIG WHERE name LIKE '%' || :keyword || '%' ORDER BY sort ASC")
    fun getSubscriptionSiteByKeyword(keyword: String): Flow<List<SiteConfig>>

    @Query("SELECT * FROM SITECONFIG WHERE id in (:ids)")
    suspend fun getSubscriptionSiteByIds(ids: List<Int>): List<SiteConfig>
}