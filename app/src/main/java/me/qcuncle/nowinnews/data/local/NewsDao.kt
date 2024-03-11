package me.qcuncle.nowinnews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.domain.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<Article>)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article WHERE siteId = :id ORDER BY position")
    fun getArticles(id: Int): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE siteId IN (:siteIds) AND position <= :count ORDER BY siteId, position")
    fun getArticles(siteIds: List<Int>, count: Int): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE title LIKE '%' || :keyword || '%' ORDER BY siteId, position")
    fun searchNews(keyword: String): Flow<List<Article>>
}