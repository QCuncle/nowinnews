package me.qcuncle.nowinnews.presentation.subscription

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.domain.usecases.node.GetSiteConfigs
import me.qcuncle.nowinnews.domain.usecases.node.InsertSiteConfig
import me.qcuncle.nowinnews.domain.usecases.node.SubscriptionSite
import me.qcuncle.nowinnews.domain.usecases.node.UnsubscribeSite
import me.qcuncle.nowinnews.presentation.subscription.compoments.SubscriptionStatus
import javax.inject.Inject

@HiltViewModel
class NodeViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val getSiteConfigs: GetSiteConfigs,
    private val subscriptionSite: SubscriptionSite,
    private val unsubscribeSite: UnsubscribeSite,
    private val insertSiteConfig: InsertSiteConfig,
) : ViewModel() {

    private val _nodeData = MutableStateFlow(emptyList<SiteConfig>())
    val nodeData: StateFlow<List<SiteConfig>> = _nodeData.asStateFlow()

    private val _dialogState = mutableStateOf(SubscriptionStatus.IDLE)
    val dialogState: State<SubscriptionStatus> = _dialogState

    private var _currentState = SubscriptionStatus.IDLE

    init {
        // Observe changes in remoteData and update _hotData accordingly
        viewModelScope.launch(Dispatchers.IO) {
            // Local data
            getSiteConfigs().collect { localData ->
                _nodeData.value = localData
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.articlesFlow
                .collect {
                    // 只有真正订阅成功才修改订阅状态
                    subscriptionSite(it.id)
                    setDialogStatus(SubscriptionStatus.SUCCESS)
                    delay(1500)
                    if (_currentState == SubscriptionStatus.SUCCESS) {
                        setDialogStatus(SubscriptionStatus.IDLE)
                    }
                }
        }
    }

    fun onEvent(event: NodeEvent) {
        when (event) {
            is NodeEvent.SubscriptionEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    // subscriptionSite(event.id)
                    sharedViewModel.refresh(event.id)
                }

                viewModelScope.launch {
                    setDialogStatus(SubscriptionStatus.SUBSCRIBING)
                    delay(8000)
                    if (_currentState == SubscriptionStatus.SUBSCRIBING) {
                        setDialogStatus(SubscriptionStatus.FAILED)
                        delay(1500)
                        if (_currentState == SubscriptionStatus.FAILED) {
                            setDialogStatus(SubscriptionStatus.IDLE)
                        }
                    }
                }
            }

            is NodeEvent.UnsubscribeEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    unsubscribeSite(event.id)
                }
            }

            is NodeEvent.ToppingEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    // 将目标 SiteConfig 置顶（将其 sort 设为最小值）
                    val updatedSiteConfigs = getSiteConfigs().first()
                    val targetSort = event.siteConfig.sort

                    // 更新其他 SiteConfig 的 sort 字段，确保排序的一致性
                    updatedSiteConfigs.forEach point@{ siteConfig ->
                        if (siteConfig.sort > targetSort) return@point
                        if (siteConfig.id == event.siteConfig.id) {
                            siteConfig.sort = 0
                        } else {
                            siteConfig.sort += 1
                        }
                    }
                    insertSiteConfig(updatedSiteConfigs)
                }
            }
        }
    }

    private fun setDialogStatus(status: SubscriptionStatus) {
        _dialogState.value = status
        _currentState = status
    }
}