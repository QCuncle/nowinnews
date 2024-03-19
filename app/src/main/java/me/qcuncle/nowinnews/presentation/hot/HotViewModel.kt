package me.qcuncle.nowinnews.presentation.hot

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.domain.usecases.news.GetHotArticles
import me.qcuncle.nowinnews.domain.usecases.userentry.ReadNewsShowNumber
import me.qcuncle.nowinnews.presentation.subscription.SharedViewModel
import me.qcuncle.nowinnews.util.findActivity
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class HotViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val getHotArticles: GetHotArticles,
    private val siteConfigDao: SiteConfigDao,
    private val showNumber: ReadNewsShowNumber,
) : ViewModel() {

    private val _hotData = MutableStateFlow(emptyList<SiteEntity>())
    val hotData: StateFlow<List<SiteEntity>> = _hotData.asStateFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isEmpty = mutableStateOf(false)
    val isEmpty: State<Boolean> = _isEmpty

    private val _refreshing = mutableStateOf(false)
    val refreshing: State<Boolean> = _refreshing

    private var _siteConfigs: List<SiteConfig> = listOf()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.articlesFlow.collect {
                updateRemoteData(it)
            }
        }
        // Observe changes in remoteData and update _hotData accordingly
        viewModelScope.launch(Dispatchers.IO) {
            // Local data
            siteConfigDao.getSubscriptionSiteConfigurations().collect { siteConfigs ->
                _siteConfigs = siteConfigs
                getHotArticles().collect { localData ->
                    // Create a mapping between id and sort
                    val idToSortMap = _siteConfigs.associate { it.id to it.sort }
                    // Sort SiteEntity based on the mapping
                    val sortedSiteEntities = localData.sortedBy { idToSortMap[it.id] }
                    _hotData.value = sortedSiteEntities
                    _isEmpty.value = sortedSiteEntities.isEmpty()
                }
            }

            delay(3000)

            siteConfigDao.getAllSiteConfigurations().collect { siteConfigs ->
                for (siteConfig in siteConfigs) {
                    val matchingEntity = _hotData.value.find { it.id == siteConfig.id }
                    if (matchingEntity != null && !siteConfig.isSubscribed) {
                        val currentHotData = _hotData.value.toMutableList()
                        // 移除已有的
                        currentHotData.remove(matchingEntity)
                        _hotData.value = currentHotData
                        _isEmpty.value = currentHotData.isEmpty()
                    } else if (matchingEntity == null && siteConfig.isSubscribed) {
                        sharedViewModel.refresh(siteConfig.id)
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            showNumber().collect {
                getHotArticles().collect { localData ->
                    // Create a mapping between id and sort
                    val idToSortMap = _siteConfigs.associate { it.id to it.sort }
                    // Sort SiteEntity based on the mapping
                    val sortedSiteEntities = localData.sortedBy { idToSortMap[it.id] }
                    _hotData.value = sortedSiteEntities
                    _isEmpty.value = sortedSiteEntities.isEmpty()
                }
            }
        }
    }

    private fun refreshById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedViewModel.refresh(id)
            }
        }
    }


    private suspend fun updateRemoteData(remoteData: SiteEntity) {
        withContext(Dispatchers.IO) {
            // Your existing code for handling remote data
            val currentHotData = _hotData.value.toMutableList()

            // Fetch the sort value for the remoteData from the corresponding SiteConfig
            val sortValue = _siteConfigs.firstOrNull { it.id == remoteData.id }?.sort

            if (sortValue != null) {
                val index = currentHotData.indexOfFirst { it.id == remoteData.id }
                if (index != -1) {
                    // _hotData already contains SiteEntity with the same id, replace it
                    currentHotData[index] = remoteData
                } else {
                    // _hotData does not contain SiteEntity with the same id, insert based on sort
                    val insertIndex = currentHotData.indexOfFirst { itSortValue ->
                        (_siteConfigs.firstOrNull { it.id == itSortValue.id }?.sort
                            ?: Int.MAX_VALUE) > sortValue
                    }
                    if (insertIndex != -1) {
                        currentHotData.add(insertIndex, remoteData)
                    } else {
                        // If insertIndex is -1, it means the remoteData has the highest sort, so append it
                        currentHotData.add(remoteData)
                    }
                }
                _hotData.value = currentHotData
                _isEmpty.value = currentHotData.isEmpty()
            }
            _isLoading.value = false
        }
    }

    fun onEvent(event: HotEvent) {
        when (event) {
            is HotEvent.RefreshAll -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val siteConfigs = siteConfigDao.getSubscriptionSiteIds()
                    if (siteConfigs.isEmpty()) {
                        _refreshing.value = false
                    } else {
                        _refreshing.value = true
                        val jobs = siteConfigs.map { id ->
                            async {
                                sharedViewModel.refresh(id)
                            }
                        }
                        jobs.awaitAll()
                        _refreshing.value = false
                    }
                }
            }

            is HotEvent.RefreshCardEvent -> {
                viewModelScope.launch {
                    refreshById(event.siteId)
                    _isLoading.value = true
                    delay(8000)
                    _isLoading.value = false
                }
            }

            is HotEvent.ShareCardEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val file = File(event.filePath)
                    // 启动协程执行保存操作
                    saveImageToFile(bitmap = event.bitmap, file = file)
                    shareImageContent(event.context, file)
                }
            }
        }
    }

    // 保存图片到文件
    private suspend fun saveImageToFile(bitmap: Bitmap?, file: File?) {
        if (bitmap == null || file == null) return
        withContext(Dispatchers.IO) {
            try {
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
            } catch (e: Exception) {
                Log.e("saveImageToFile", e.stackTraceToString())
            }
        }
    }

    private fun shareImageContent(context: Context, imageFile: File) {
        // Check if the file exists before attempting to share
        if (imageFile.exists()) {
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooserIntent = Intent.createChooser(shareIntent, "分享至")
            context.findActivity().startActivity(chooserIntent)
        } else {
            // Handle the case where the image file does not exist
            // You might want to show an error message to the user
        }
    }
}