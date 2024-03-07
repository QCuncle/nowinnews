package me.qcuncle.nowinnews.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    /**
     *App 首次进入保存状态
     */
    suspend fun saveAppEntry()

    /**
     * 读取首次进入 App 状态
     */
    fun readAppEntry(): Flow<Boolean>

    /**
     * 设置主题色配置
     */
    suspend fun saveThemeConfig(index: Int)

    /**
     *读取主题配置
     */
    fun readThemeColor(): Flow<Int>

    /**
     * 保存应用深色模式配置
     * @param value 0-开启 1-关闭 2-跟随系统
     */
    suspend fun saveDarkMode(value: Int)

    /**
     *设置应用深色模式配置
     */
    fun readDarkMode(): Flow<Int>
}