package me.qcuncle.nowinnews.presentation.hot.compoments

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.delay
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.SiteEntity
import me.qcuncle.nowinnews.presentation.common.NewsCancelTextButton
import me.qcuncle.nowinnews.presentation.common.NewsTextButton
import me.qcuncle.nowinnews.util.TimeFormat
import me.qcuncle.nowinnews.util.formatTime
import java.io.File

@OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeApi::class)
@Composable
fun CardShareDialog(
    siteEntity: SiteEntity,
    onDismiss: () -> Unit,
    onShare: (Bitmap?, String) -> Unit
) {
    val imageDirectory = LocalContext.current.externalCacheDir
    imageDirectory?.mkdirs()
    val captureController = rememberCaptureController()
    var cardScreenshot by remember { mutableStateOf<Bitmap?>(null) }
    var filePath by remember { mutableStateOf("") }
    var buttonPressed by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                text = stringResource(R.string.share_dialog_title, siteEntity.name),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            SharedCard(
                modifier = Modifier.capturable(captureController),
                siteEntity = siteEntity
            )
            Row(modifier = Modifier.padding(bottom = 12.dp, end = 8.dp)) {

                Spacer(modifier = Modifier.weight(1f))

                NewsCancelTextButton(
                    text = stringResource(R.string.cancel),
                ) {
                    onDismiss()
                }

                NewsTextButton(
                    text = stringResource(R.string.share),
                ) {
                    filePath = imageDirectory?.absolutePath + File.separator +
                            siteEntity.articles[0].updateTime.formatTime(TimeFormat.FORMAT0) + ".png"
                    onShare(cardScreenshot, filePath)
                    onDismiss()
                    buttonPressed = true
                }

                LaunchedEffect(Unit) {
                    delay(300L)
                    val bitmapAsync = captureController.captureAsync()
                    try {
                        cardScreenshot = bitmapAsync.await().asAndroidBitmap()
                    } catch (error: Throwable) {
                        // Error occurred, do something.
                    }
                }
            }
        }
    }
}

@Composable
fun SharedCard(
    modifier: Modifier = Modifier,
    siteEntity: SiteEntity,
) {
    Card(
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
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
            }
            Spacer(modifier = Modifier.height(4.dp))

            Column {
                siteEntity.articles.forEach { article ->
                    HotArticleItemWithoutEvent(
                        article = article
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp),
                    contentScale = ContentScale.Crop
                )

                Column {
                    Text(
                        text = "卡片由小鱼报App生成",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )

                    Text(
                        text = siteEntity.articles[0].updateTime.formatTime(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp),
                    )

                }
            }
        }
    }
}

@Composable
fun HotArticleItemWithoutEvent(
    article: Article
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
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
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        val textStyle = if (article.position > 3) MaterialTheme.typography.bodyMedium
        else MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
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
