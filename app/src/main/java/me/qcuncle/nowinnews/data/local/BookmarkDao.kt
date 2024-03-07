package me.qcuncle.nowinnews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.qcuncle.nowinnews.domain.model.Bookmark

@Dao
interface BookmarkDao {
    /**
     * 收藏书签
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookMark: Bookmark)

    /**
     * 删除书签
     */
    @Query("DELETE from Bookmark Where url = :url")
    suspend fun remove(url: String)

    /**
     * 查询所有收藏
     */
    @Query("SELECT * from Bookmark ORDER BY collectionTime DESC")
    fun getAll(): Flow<List<Bookmark>>
}