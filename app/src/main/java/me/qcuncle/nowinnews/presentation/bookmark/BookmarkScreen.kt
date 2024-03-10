package me.qcuncle.nowinnews.presentation.bookmark

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.domain.model.Bookmark
import me.qcuncle.nowinnews.presentation.common.NewsCancelTextButton
import me.qcuncle.nowinnews.presentation.common.NewsTextButton
import me.qcuncle.nowinnews.ui.components.EmptyView
import me.qcuncle.nowinnews.ui.components.ThemedXmlDrawable
import me.qcuncle.nowinnews.util.formatDisplayTime
import me.qcuncle.nowinnews.util.jumpToBrowser

@Composable
fun BookmarkScreen(
    bookmarks: List<Bookmark>,
    isEmpty: Boolean,
    event: (BookmarkEvent) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var delArticleUrl by remember { mutableStateOf<String>("") }

    LazyColumn {
        items(bookmarks) { bookmark ->
            BookMarkItem(
                bookmark,
                deleteBookmark = { url ->
                    delArticleUrl = url
                    showDeleteDialog = true
                }
            )
        }
    }

    if (isEmpty) {
        EmptyView(
            iconResId = R.drawable.empty_bookmark,
            text = stringResource(R.string.empty_bookmark_screen)
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            text = {
                Text(
                    text = stringResource(R.string.dialog_title_delete_bookmark),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            onDismissRequest = {
                showDeleteDialog = false
            },
            dismissButton = {
                NewsTextButton(
                    text = stringResource(id = R.string.cancel),
                ) {
                    showDeleteDialog = false
                }
            },
            confirmButton = {
                NewsTextButton(
                    text = stringResource(id = R.string.confirm),
                ) {
                    showDeleteDialog = false
                    event(BookmarkEvent.DeleteEvent(delArticleUrl))
                }
            }
        )
    }
}

@Composable
fun BookMarkItem(
    bookmark: Bookmark,
    deleteBookmark: (String) -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .clickable {
                context.jumpToBrowser(bookmark.url)
            }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (bookmark.imageUrl.isEmpty()) {
            ThemedXmlDrawable(
                drawableResId = R.drawable.baseline_link_48,
                modifier = Modifier
                    .width(150.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(bookmark.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = bookmark.title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 18.sp
            )
            Text(
                text = bookmark.popularity,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (bookmark.siteIconUrl.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = bookmark.siteName.first().toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
                            textAlign = TextAlign.Center,
                            fontSize = 8.sp
                        )
                    }
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(bookmark.siteIconUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.baseline_public_24),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${bookmark.collectionTime.formatDisplayTime()}收藏",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                )

                Spacer(modifier = Modifier.weight(1f))

                NewsCancelTextButton(text = "删除") {
                    deleteBookmark(bookmark.url)
                }
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.2.dp)
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    )
}