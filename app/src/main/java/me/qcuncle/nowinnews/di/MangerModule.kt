package me.qcuncle.nowinnews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.qcuncle.nowinnews.data.manager.LocalUserManagerImpl
import me.qcuncle.nowinnews.data.manager.SubscriptionManagerImpl
import me.qcuncle.nowinnews.data.remote.NewsRequestImpl
import me.qcuncle.nowinnews.domain.manager.LocalUserManager
import me.qcuncle.nowinnews.domain.manager.SubscriptionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MangerModule {

    @Binds
    @Singleton
    abstract fun bindLocalUserManger(localUserManagerImpl: LocalUserManagerImpl): LocalUserManager

    @Binds
    @Singleton
    abstract fun bindSubscriptionManager(subscriptionManagerImpl: SubscriptionManagerImpl): SubscriptionManager

    @Binds
    @Singleton
    abstract fun bindNewsRequestImpl(newsRequestImpl: NewsRequestImpl): NewsRequestImpl
}