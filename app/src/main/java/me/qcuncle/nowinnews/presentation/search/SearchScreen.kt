package me.qcuncle.nowinnews.presentation.search

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.domain.model.Article
import me.qcuncle.nowinnews.domain.model.Bookmark
import me.qcuncle.nowinnews.domain.model.SiteConfig
import me.qcuncle.nowinnews.presentation.common.NewsButton
import me.qcuncle.nowinnews.presentation.common.NewsCancelButton
import me.qcuncle.nowinnews.presentation.subscription.compoments.SubscriptionDialog
import me.qcuncle.nowinnews.presentation.subscription.compoments.SubscriptionStatus
import me.qcuncle.nowinnews.ui.components.EmptyView
import me.qcuncle.nowinnews.ui.components.ThemedXmlDrawable
import me.qcuncle.nowinnews.util.formatDisplayTime
import me.qcuncle.nowinnews.util.jumpToBrowser

@Composable
fun SearchScreen(
    articles: List<Article>,
    siteConfigs: List<SiteConfig>,
    bookmarks: List<Bookmark>,
    dialogState: SubscriptionStatus,
    isEmpty: Boolean,
    event: (SearchEvent) -> Unit,
    onBackClick: () -> Unit
) {
    var keyword by remember { mutableStateOf("") }
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.empty_search_screen_prefix))
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(" $keyword ")
        }
        append(stringResource(id = R.string.empty_search_screen_suffix))
    }

    if (isEmpty && keyword.isNotEmpty()) {
        EmptyView(
            iconResId = R.drawable.empty_search,
            text = annotatedString,
            percentage = 0.5f
        )
    }
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            SearchScreenTopBar(
                onKeyWordChanged = {
                    keyword = it
                    event(SearchEvent.Search(keyword.trim()))
                },
                onBackClick = onBackClick
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                if (keyword.isNotEmpty()) {
                    if (siteConfigs.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.subscription),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                        )
                        LazyColumn {
                            items(siteConfigs) { siteConfig ->
                                SearchSiteItem(siteConfig = siteConfig, event = event)
                            }
                        }
                    }

                    if (articles.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.content),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                        )
                        LazyColumn {
                            items(articles) { article ->
                                SearchArticleItem(
                                    siteName = article.siteName,
                                    title = article.title,
                                    popularity = article.popularity,
                                    url = article.url,
                                    imageUrl = article.imageUrl,
                                    siteIconUrl = article.siteIconUrl,
                                    displayContent = article.updateTime.formatDisplayTime()
                                )
                            }
                        }
                    }

                    if (bookmarks.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.bookmark),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                        )
                        LazyColumn {
                            items(bookmarks) { bookmark ->
                                SearchArticleItem(
                                    siteName = bookmark.siteName,
                                    title = bookmark.title,
                                    popularity = bookmark.popularity,
                                    url = bookmark.url,
                                    imageUrl = bookmark.imageUrl,
                                    siteIconUrl = bookmark.siteIconUrl,
                                    displayContent = bookmark.collectionTime.formatDisplayTime() + "收藏"
                                )
                            }
                        }
                    }
                }
            }
        }

        when (dialogState) {
            SubscriptionStatus.SUBSCRIBING -> {
                SubscriptionDialog(
                    status = SubscriptionStatus.SUBSCRIBING,
                    modifier = Modifier.padding(100.dp)
                )
            }

            SubscriptionStatus.SUCCESS -> {
                SubscriptionDialog(
                    status = SubscriptionStatus.SUCCESS,
                    modifier = Modifier.padding(100.dp)
                )
            }

            SubscriptionStatus.FAILED -> {
                SubscriptionDialog(
                    status = SubscriptionStatus.FAILED,
                    modifier = Modifier.padding(100.dp)
                )
            }

            else -> {}
        }
    }
}


@Composable
fun SearchScreenTopBar(
    onKeyWordChanged: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var keyword by remember { mutableStateOf("") }

    LaunchedEffect(keyboardController) {
        // Automatically request focus when the composable is first laid out
        focusRequester.requestFocus()
        // Show the software keyboard
        keyboardController?.show()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        ThemedXmlDrawable(
            drawableResId = (R.drawable.baseline_arrow_back_24),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .clickable {
                    onBackClick()
                }
                .padding(8.dp)
        )

        OutlinedTextField(
            value = keyword,
            onValueChange = { input ->
                keyword = input
                onKeyWordChanged(input)
            },
            maxLines = 1,
            modifier = Modifier
                .weight(8f)
                .focusRequester(focusRequester)
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    CircleShape
                ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            shape = CircleShape,
            leadingIcon = {
                ThemedXmlDrawable(
                    drawableResId = R.drawable.baseline_search_24,
                    modifier = Modifier
                )
            },
            trailingIcon = {
                ThemedXmlDrawable(
                    drawableResId = R.drawable.baseline_close_24,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            keyword = ""
                            onKeyWordChanged("")
                        }
                        .padding(8.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SearchSiteItem(
    siteConfig: SiteConfig,
    event: (SearchEvent) -> Unit,
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

        if (siteConfig.isSubscribed) {
            NewsCancelButton(text = stringResource(R.string.unsubscribe)) {
                event(SearchEvent.UnsubscribeEvent(siteConfig.id))
            }
        } else {
            NewsButton(text = stringResource(id = R.string.subscription)) {
                event(SearchEvent.SubscriptionEvent(siteConfig.id))
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

@Composable
fun SearchArticleItem(
    siteName: String,
    title: String,
    popularity: String,
    url: String,
    imageUrl: String,
    siteIconUrl: String,
    displayContent: String,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .clickable {
                context.jumpToBrowser(url)
            }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (imageUrl.isEmpty()) {
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
                    .data(imageUrl)
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
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 18.sp
            )
            Text(
                text = popularity,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (siteIconUrl.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = siteName.first().toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
                            textAlign = TextAlign.Center,
                            fontSize = 8.sp
                        )
                    }
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(siteIconUrl)
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
                    text = "$siteName · $displayContent",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                )
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