package me.qcuncle.nowinnews.presentation.viewmore

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.presentation.common.NewsButton
import me.qcuncle.nowinnews.ui.theme.NinTheme
import me.qcuncle.nowinnews.util.formatTime
import me.qcuncle.nowinnews.util.jumpToBrowser
import me.qcuncle.nowinnews.util.showToast

@Composable
fun ViewMoreScreen(
    siteEntity: SiteEntity?,
    event: (ViewMoreEvent) -> Unit,
) {
    Column {
        if (siteEntity == null) {
            // todo add an empty view for view more screen
        } else {
            ViewMoreTopBar(
                time = siteEntity.articles[0].updateTime,
                siteEntity = siteEntity,
            )
            LazyColumn {
                itemsIndexed(siteEntity.articles) { index, article ->
                    val showLine = index < siteEntity.articles.size - 1
                    ViewMoreItem(
                        article = article,
                        showLine,
                        addBookmark = {
                            event(ViewMoreEvent.AddBookmark(it))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ViewMoreTopBar(
    time: Long,
    siteEntity: SiteEntity
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (siteEntity.siteIcon.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
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
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Column(
            modifier = Modifier
                .weight(8f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = siteEntity.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = time.formatTime(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

//        RotateAnimatedImageButton(
//            painter = painterResource(id = R.drawable.baseline_icon_refresh_24),
//            modifier = Modifier
//                .weight(1f)
//                .size(32.dp)
//                .padding(6.dp)
//                .clip(CircleShape),
//            rotationDegree = 360f,
//            direction = Direction.CLOCKWISE,
//            onClick = {
//
//            },
//        )
    }
}

@Composable
fun ViewMoreItem(
    article: Article,
    showLine: Boolean,
    addBookmark: (Article) -> Unit,
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                context.jumpToBrowser(article.url)
            }
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))

    ) {
        val backgroundColor = when (article.position) {
            1 -> Color(0xFFEA444D)
            2 -> Color(0xFFED702D)
            3 -> Color(0xFFEEAD3F)
            else -> MaterialTheme.colorScheme.surfaceVariant
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

        Column(
            modifier = Modifier
                .weight(8f)
                .padding(horizontal = 16.dp)
        ) {
            val textStyle = if (article.position > 3) MaterialTheme.typography.bodyLarge
            else MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            Text(
                text = article.title,
                textAlign = TextAlign.Start,
                style = textStyle,
                lineHeight = 18.sp
            )
            Text(
                text = article.popularity,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        NewsButton(
            text = stringResource(R.string.collection),
            modifier = Modifier.weight(2f)
        ) {
            context.showToast(R.string.bookmarked)
            addBookmark(article)
        }
    }

    if (showLine) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.2.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun ArticleItemPreview() {
    NinTheme {
        Surface {
            ViewMoreItem(
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
                ), true
            ) {}
        }
    }
}