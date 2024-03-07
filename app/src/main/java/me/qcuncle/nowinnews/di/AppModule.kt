package me.qcuncle.nowinnews.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.qcuncle.nowinnews.data.local.BookmarkDao
import me.qcuncle.nowinnews.data.local.BookmarkDatabase
import me.qcuncle.nowinnews.data.local.NewsDao
import me.qcuncle.nowinnews.data.local.NewsDatabase
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.data.local.SiteConfigDatabase
import me.qcuncle.nowinnews.data.local.SiteConfigTypeConvertor
import me.qcuncle.nowinnews.data.remote.NewsApi
import me.qcuncle.nowinnews.data.remote.NewsRequestImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApiInstance(
        application: Application
    ): NewsApi {
        return NewsRequestImpl(application)
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSiteConfigDatabase(
        application: Application
    ): SiteConfigDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = SiteConfigDatabase::class.java,
            name = "site_config_db"
        ).addTypeConverter(SiteConfigTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBookMarkDatabase(
        application: Application
    ): BookmarkDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = BookmarkDatabase::class.java,
            name = "bookmark_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

    @Provides
    @Singleton
    fun provideSiteConfigDao(
        siteConfigDatabase: SiteConfigDatabase
    ): SiteConfigDao = siteConfigDatabase.siteConfigDao

    @Provides
    @Singleton
    fun provideBookmarkDao(
        bookMarkDatabase: BookmarkDatabase
    ): BookmarkDao = bookMarkDatabase.bookMarkDao
}