package me.qcuncle.nowinnews.presentation.viewmore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.domain.usecases.bookmark.AddBookmark
import me.qcuncle.nowinnews.domain.usecases.news.GetHotArticlesBySiteId
import javax.inject.Inject

@HiltViewModel
class ViewMoreViewModel @Inject constructor(
    private val getHotArticlesBySiteId: GetHotArticlesBySiteId,
    private val addBookmark: AddBookmark,
) : ViewModel() {

    private val _data = MutableStateFlow<SiteEntity?>(null)
    val data: StateFlow<SiteEntity?> = _data.asStateFlow()

    fun loadData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getHotArticlesBySiteId(id).collect { siteEntity ->
                _data.value = siteEntity
            }
        }
    }

    fun onEvent(event: ViewMoreEvent) {
        when (event) {
            is ViewMoreEvent.AddBookmark -> {
                viewModelScope.launch(Dispatchers.IO) {
                    addBookmark(event.article)
                }
            }
        }
    }
}