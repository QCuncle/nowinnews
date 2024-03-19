package me.qcuncle.nowinnews.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.qcuncle.nowinnews.data.local.SiteConfigDao
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.Bookmark
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.usecases.bookmark.AddBookmark
import me.qcuncle.nowinnews.domain.usecases.node.SubscriptionSite
import me.qcuncle.nowinnews.domain.usecases.node.UnsubscribeSite
import me.qcuncle.nowinnews.domain.usecases.search.SearchArticlesByKeyword
import me.qcuncle.nowinnews.domain.usecases.search.SearchBookmarkByKeyword
import me.qcuncle.nowinnews.presentation.subscription.SharedViewModel
import me.qcuncle.nowinnews.presentation.subscription.compoments.SubscriptionStatus
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val searchArticlesByKeyword: SearchArticlesByKeyword,
    private val siteConfigDao: SiteConfigDao,
    private val searchBookmarkByKeyword: SearchBookmarkByKeyword,
    private val subscriptionSite: SubscriptionSite,
    private val unsubscribeSite: UnsubscribeSite,
    private val addBookmark: AddBookmark
) : ViewModel() {

    private val _articles = mutableStateOf(emptyList<Article>())
    val articles: State<List<Article>> = _articles

    private val _siteConfigs = mutableStateOf(emptyList<SiteConfig>())
    val siteConfig: State<List<SiteConfig>> = _siteConfigs

    private val _bookmarks = mutableStateOf(emptyList<Bookmark>())
    val bookmarks: State<List<Bookmark>> = _bookmarks

    private val _dialogState = mutableStateOf(SubscriptionStatus.IDLE)
    val dialogState: State<SubscriptionStatus> = _dialogState

    private var _currentState = SubscriptionStatus.IDLE

    private val _isEmpty = mutableStateOf(true)
    val isEmpty: State<Boolean> = _isEmpty

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.articlesFlow
                .collect {
                    // 只有真正订阅成功才修改订阅状态
                    subscriptionSite(it.id)
                    sharedViewModel.subscriptionMap[it.id] = true
                    updateSiteConfig()
                    setDialogStatus(SubscriptionStatus.SUCCESS)
                    delay(1500)
                    if (_currentState == SubscriptionStatus.SUCCESS) {
                        setDialogStatus(SubscriptionStatus.IDLE)
                    }
                }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.Search -> {
                search(event.keyword)
            }

            is SearchEvent.Subscription -> {
                viewModelScope.launch(Dispatchers.IO) {
                    // subscriptionSite(event.id)
                    sharedViewModel.refresh(event.id)
                }

                viewModelScope.launch {
                    setDialogStatus(SubscriptionStatus.SUBSCRIBING)
                    delay(8000)
                    if (sharedViewModel.subscriptionMap[event.id] != true && _currentState == SubscriptionStatus.SUBSCRIBING) {
                        setDialogStatus(SubscriptionStatus.FAILED)
                        delay(1500)
                        if (_currentState == SubscriptionStatus.FAILED) {
                            setDialogStatus(SubscriptionStatus.IDLE)
                        }
                    }
                }
            }

            is SearchEvent.Unsubscribe -> {
                viewModelScope.launch(Dispatchers.IO) {
                    unsubscribeSite(event.id)
                    sharedViewModel.subscriptionMap[event.id] = false
                    updateSiteConfig()
                }
            }

            is SearchEvent.Collect -> {
                viewModelScope.launch(Dispatchers.IO) {
                    addBookmark.invoke(event.article)
                }
            }
        }
    }

    private fun search(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val articles = async { searchArticlesByKeyword(keyword).first() }
            val siteConfigs = async { siteConfigDao.getSubscriptionSiteByKeyword(keyword).first() }
            val bookmarks = async { searchBookmarkByKeyword(keyword).first() }
            _articles.value = if (keyword.isEmpty()) emptyList() else articles.await()
            _siteConfigs.value = if (keyword.isEmpty()) emptyList() else siteConfigs.await()
            _bookmarks.value = if (keyword.isEmpty()) emptyList() else bookmarks.await()
            _isEmpty.value = articles.await().isEmpty()
                    && siteConfigs.await().isEmpty()
                    && bookmarks.await().isEmpty()
        }
    }

    private fun setDialogStatus(status: SubscriptionStatus) {
        _dialogState.value = status
        _currentState = status
    }

    private fun updateSiteConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            val ids: List<Int> = _siteConfigs.value.map { it.id }
            val tempSiteConfigs = siteConfigDao.getSubscriptionSiteByIds(ids)
            _siteConfigs.value = tempSiteConfigs
        }
    }
}