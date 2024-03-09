package me.qcuncle.nowinnews.presentation.subscription

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.domain.model.ArticleXpath
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.presentation.common.NewsButton
import me.qcuncle.nowinnews.presentation.common.NewsCancelButton
import me.qcuncle.nowinnews.presentation.subscription.compoments.SubscriptionDialog
import me.qcuncle.nowinnews.presentation.subscription.compoments.SubscriptionStatus
import me.qcuncle.nowinnews.ui.components.ThemedXmlDrawable
import me.qcuncle.nowinnews.ui.theme.NinTheme

@Composable
fun NodeListScreen(
    siteConfigs: List<SiteConfig>,
    dialogStatus: SubscriptionStatus,
    event: (NodeEvent) -> Unit
) {
    Box(contentAlignment = Alignment.TopCenter) {
        Column {
            Text(
                text = stringResource(R.string.node_screen_top_tip),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            LazyColumn {
                items(siteConfigs) { siteConfig ->
                    SiteItem(siteConfig = siteConfig, event)
                }
            }
        }

        when (dialogStatus) {
            SubscriptionStatus.SUBSCRIBING -> {
                SubscriptionDialog(status = SubscriptionStatus.SUBSCRIBING)
            }

            SubscriptionStatus.SUCCESS -> {
                SubscriptionDialog(status = SubscriptionStatus.SUCCESS)
            }

            SubscriptionStatus.FAILED -> {
                SubscriptionDialog(status = SubscriptionStatus.FAILED)
            }

            else -> {}
        }
    }
}

@Composable
fun SiteItem(
    siteConfig: SiteConfig,
    event: (NodeEvent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
    ) {
        if (siteConfig.siteIconUrl.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 8.dp)
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = siteConfig.name.first().toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(siteConfig.siteIconUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.baseline_public_24),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 8.dp)
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Text(
            text = siteConfig.name,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            lineHeight = 20.sp,
            modifier = Modifier.fillMaxWidth(0.4f)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (siteConfig.isSubscribed && siteConfig.sort > 0) {
            ThemedXmlDrawable(
                drawableResId = R.drawable.baseline_topping_24,
                iconColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = {
                            event(NodeEvent.ToppingEvent(siteConfig))
                        }
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        if (siteConfig.isSubscribed) {
            NewsCancelButton(text = "取消订阅") {
                event(NodeEvent.UnsubscribeEvent(siteConfig.id))
            }
        } else {
            NewsButton(text = "订阅") {
                event(NodeEvent.SubscriptionEvent(siteConfig.id))
            }
        }

        Spacer(modifier = Modifier.size(16.dp))
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.2.dp)
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SiteItemPreview() {
    NinTheme {
        SiteItem(
            SiteConfig(
                id = 0,
                sort = 0,
                isSubscribed = true,
                host = "",
                name = "百度",
                siteIconUrl = "",
                siteUrl = "",
                delay = 50,
                articleXpath = ArticleXpath(
                    position = "",
                    title = "百度热搜",
                    imageUrl = "",
                    popularity = "1433223",
                    url = "",
                    parameter = ""
                )
            )
        ) {}
    }
}