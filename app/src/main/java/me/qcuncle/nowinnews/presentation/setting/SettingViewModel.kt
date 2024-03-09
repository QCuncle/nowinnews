package me.qcuncle.nowinnews.presentation.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.usecases.node.GetSiteConfigs
import me.qcuncle.nowinnews.domain.usecases.userentry.ConfigureSite
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadDarkMode
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadNewsShowNumber
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadThemeConfig
import me.qcuncle.nowinnews.domain.usecases.userentry.ResetSiteConfigure
import me.qcuncle.nowinnews.domain.usecases.userentry.SaveDarkMode
import me.qcuncle.nowinnews.domain.usecases.userentry.SaveNewsShowNumber
import me.qcuncle.nowinnews.domain.usecases.userentry.SaveThemeConfig
import me.qcuncle.nowinnews.util.Constant
import me.qcuncle.nowinnews.util.findActivity
import me.qcuncle.nowinnews.util.md5
import me.qcuncle.nowinnews.util.showToast
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val readNewsShowNumber: ReadNewsShowNumber,
    private val saveNewsShowNumber: SaveNewsShowNumber,
    private val readDarkMode: ReadDarkMode,
    private val saveDarkMode: SaveDarkMode,
    private val readThemeConfig: ReadThemeConfig,
    private val saveThemeConfig: SaveThemeConfig,
    private val resetSiteConfigure: ResetSiteConfigure,
    private val configureSite: ConfigureSite,
    private val getSiteConfigs: GetSiteConfigs,
) : ViewModel() {

    private val _showNum = MutableStateFlow(Constant.NUMBER_OF_ARTICLES_DEFAULT)
    val showNum: StateFlow<Int> = _showNum.asStateFlow()

    private val _darkMode = MutableStateFlow(Constant.DARK_MODE_DEFAULT)
    val darkMode: StateFlow<Int> = _darkMode

    private val _themeIndex = MutableStateFlow(Constant.THEME_CONFIG_DEFAULT)
    val themeIndex: StateFlow<Int> = _themeIndex

    init {
        viewModelScope.launch(Dispatchers.IO) {

            launch {
                readNewsShowNumber().collect { num ->
                    _showNum.value = num
                }
            }

            launch {
                readDarkMode().collect { mode ->
                    _darkMode.value = mode
                }
            }

            launch {
                readThemeConfig().collect { config ->
                    _themeIndex.value = config
                }
            }
        }
    }

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.SaveShowNum -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveNewsShowNumber(event.num)
                }
            }

            is SettingEvent.SaveDarkMode -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveDarkMode(event.darkMode)
                }
            }

            is SettingEvent.SaveThemeConfig -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveThemeConfig(event.themeIndex)
                }
            }

            is SettingEvent.RestSubscriptionConfiguration -> {
                viewModelScope.launch {
                    resetSiteConfigure()
                }
            }

            is SettingEvent.UploadSubscriptionConfiguration -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val tip = event.jsonString?.let {
                        if (configureSite(event.jsonString)) "配置成功" else "配置失败，请检查规则是否正确"
                    } ?: "配置为空"
                    withContext(Dispatchers.Main) {
                        event.context.showToast(tip)
                    }
                }
            }

            is SettingEvent.ExportFile -> {
                viewModelScope.launch {
                    exportAndShareJsonFile(event.context)
                }
            }
        }
    }

    private suspend fun exportAndShareJsonFile(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val siteConfigs = getSiteConfigs().first()
                val jsonString = Json.encodeToString(
                    serializer = ListSerializer(SiteConfig.serializer()),
                    value = siteConfigs
                )

                val fileName = "订阅配置[${jsonString.md5().substring(0, 8)}].json"

                val cacheDirectory = context.externalCacheDir
                cacheDirectory?.mkdirs()
                if (cacheDirectory == null) {
                    context.showToast("文件创建失败")
                    return@withContext
                }

                val filePath: String = cacheDirectory.absolutePath + File.separator + fileName

                val file = File(filePath)
                // 创建文件父目录
                file.parentFile?.mkdirs()
                // 将 JSON 字符串写入文件
                file.writeText(jsonString)
                val uri: Uri = FileProvider
                    .getUriForFile(context, "${context.packageName}.fileprovider", file)
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "application/json"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                val chooserIntent = Intent.createChooser(shareIntent, "分享至")
                context.findActivity().startActivity(chooserIntent)
            } catch (e: Exception) {
                context.showToast(e.message)
            }
        }
    }
}