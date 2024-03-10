package me.qcuncle.nowinnews.presentation.hot

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.presentation.hot.compoments.CardShareDialog
import me.qcuncle.nowinnews.presentation.hot.compoments.CircularProgressIndicatorWithTimeout
import me.qcuncle.nowinnews.presentation.hot.compoments.HotArticleCard
import me.qcuncle.nowinnews.ui.components.EmptyView
import me.qcuncle.nowinnews.ui.theme.NinTheme

@Composable
fun HotListScreen(
    isLoading: Boolean,
    isEmpty: Boolean,
    siteEntities: List<SiteEntity>,
    onBottomBarVisible: (Boolean) -> Unit,
    event: (HotEvent) -> Unit,
    viewMore: (Int) -> Unit,
) {
    var showShareDialog by remember {
        mutableStateOf(false)
    }

    var shareCardEntity by remember {
        mutableStateOf<SiteEntity?>(null)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return if (available.y > 0) {
                    onBottomBarVisible(true)
                    Offset.Zero
                } else if (available.y < 0) {
                    onBottomBarVisible(false)
                    Offset.Zero
                } else {
                    super.onPreScroll(available, source)
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            items(siteEntities) { siteEntity ->
                HotArticleCard(
                    siteEntity = siteEntity,
                    refreshAction = {
                        event(HotEvent.RefreshCardEvent(siteEntity.id))
                    },
                    shareAction = {
                        //event(HotEvent.ShareCardEvent(siteEntity))
                        shareCardEntity = it
                        showShareDialog = true
                    },
                    viewMore = { id -> viewMore(id) }
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicatorWithTimeout()
        }

        if (showShareDialog && shareCardEntity != null) {
            val context = LocalContext.current
            CardShareDialog(
                siteEntity = shareCardEntity!!,
                onDismiss = { showShareDialog = false },
                onShare = { bitmap, filePath ->
                    event(HotEvent.ShareCardEvent(context, bitmap, filePath))
                }
            )
        }

        if (isEmpty) {
            EmptyView(
                iconResId = R.drawable.empty_subscription,
                text = stringResource(R.string.empty_hot_screen)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun HotListScreenPreview() {
    NinTheme {
    }
}