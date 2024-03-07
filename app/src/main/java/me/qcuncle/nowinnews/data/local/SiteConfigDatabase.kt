package me.qcuncle.nowinnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.qcuncle.nowinnews.domain.model.SiteConfig

@Database(entities = [SiteConfig::class], version = 1)
@TypeConverters(SiteConfigTypeConvertor::class)
abstract class SiteConfigDatabase : RoomDatabase() {

    abstract val siteConfigDao: SiteConfigDao

}