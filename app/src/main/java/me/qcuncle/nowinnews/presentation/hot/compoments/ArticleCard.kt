package me.qcuncle.nowinnews.presentation.hot.compoments

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.presentation.common.NewsTextButton
import me.qcuncle.nowinnews.ui.components.ThemedXmlDrawable
import me.qcuncle.nowinnews.ui.theme.NinTheme
import me.qcuncle.nowinnews.util.formatDisplayTime
import me.qcuncle.nowinnews.util.jumpToBrowser

@Composable
fun HotArticleCard(
    siteEntity: SiteEntity,
    refreshAction: () -> Unit = {},
    shareAction: (SiteEntity) -> Unit = {},
    viewMore: (Int) -> Unit = {}
) {
    // 使用 remember 和 mutableStateOf 来维护卡片的数据状态
    val articles = siteEntity.articles

    Card(
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                top = 12.dp,
                bottom = 6.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                if (siteEntity.siteIcon.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = siteEntity.name.first().toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(siteEntity.siteIcon)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.baseline_public_24),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Text(
                    text = siteEntity.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 4.dp),
                )

                Spacer(modifier = Modifier.weight(1f))

                ThemedXmlDrawable(
                    drawableResId = R.drawable.baseline_share_24,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .clickable {
                            shareAction(siteEntity)
                        }
                        .padding(4.dp),
                    iconColor = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))

                RotateAnimatedImageButton(
                    drawableId = R.drawable.baseline_refresh_24,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape),
                    rotationDegree = 360f,
                    direction = Direction.CLOCKWISE,
                    onClick = {
                        refreshAction()
                    },
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Column {
                articles.forEach { article ->
                    HotArticleItem(
                        article = article
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = articles[0].updateTime.formatDisplayTime(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp),
                )

                Spacer(modifier = Modifier.weight(1f))

                NewsTextButton(text = "查看更多") {
                    viewMore(siteEntity.id)
                }
            }
        }
    }
}

@Composable
fun HotArticleItem(
    article: Article
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                context.jumpToBrowser(article.url)
            }
            .padding(horizontal = 16.dp)
    ) {
        val backgroundColor = when (article.position) {
            1 -> Color(0xFFEA444D)
            2 -> Color(0xFFED702D)
            3 -> Color(0xFFEEAD3F)
            else -> MaterialTheme.colorScheme.onSecondary
        }
        val textColor =
            if (article.position > 3) Color.Unspecified else colorResource(id = R.color.white)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
        ) {
            Text(
                text = article.position.toString(),
                color = textColor,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        val textStyle = if (article.position > 3) MaterialTheme.typography.bodyLarge
        else MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        Text(
            text = article.title,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = textStyle,
            modifier = Modifier.padding(start = 6.dp, top = 8.dp, bottom = 8.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun ArticleItemPreview() {
    NinTheme {
        HotArticleCard(
            SiteEntity(
                id = 0,
                siteIcon = "",
                name = "百度",
                articles = listOf(
                    Article(
                        siteId = 0,
                        siteIconUrl = "https://www.baidu.com/favicon.ico",
                        siteName = "百度",
                        position = 1,
                        title = "百度热搜",
                        updateTime = System.currentTimeMillis(),
                        imageUrl = "",
                        popularity = "1433223",
                        url = ""
                    )
                )
            )
        )
    }
}