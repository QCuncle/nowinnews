package me.qcuncle.nowinnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.qcuncle.nowinnews.domain.model.Article

@Database(entities = [Article::class], version = 2)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDao: NewsDao

}