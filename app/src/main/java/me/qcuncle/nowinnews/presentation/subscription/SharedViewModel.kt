package me.qcuncle.nowinnews.presentation.subscription

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.firstOrNull
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.domain.usecases.news.RefreshArticles
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedViewModel @Inject constructor(
    private val refreshArticles: RefreshArticles,
) : ViewModel() {
    private val _articlesFlow = MutableSharedFlow<SiteEntity>() // 假设你的文章是Article类
    val articlesFlow: SharedFlow<SiteEntity> get() = _articlesFlow

    suspend fun refresh(id: Int) {
        refreshArticles(id).firstOrNull()?.let {
            _articlesFlow.emit(it)
        }
    }
}