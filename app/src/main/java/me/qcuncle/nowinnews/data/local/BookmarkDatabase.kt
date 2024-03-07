package me.qcuncle.nowinnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.qcuncle.nowinnews.domain.model.Bookmark

@Database(entities = [Bookmark::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract val bookMarkDao: BookmarkDao

}