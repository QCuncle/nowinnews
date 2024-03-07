package me.qcuncle.nowinnews.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.qcuncle.nowinnews.presentation.nvgraph.Route
import me.qcuncle.nowinnews.presentation.common.NewsButton
import me.qcuncle.nowinnews.presentation.common.NewsTextButton
import me.qcuncle.nowinnews.presentation.onboarding.compoments.OnBoardingPage
import me.qcuncle.nowinnews.presentation.onboarding.compoments.PageIndicator
import me.qcuncle.nowinnews.ui.theme.NinTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(1f)
            .background(MaterialTheme.colorScheme.background)
    ) {
        val pageState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        val buttonState = remember {
            derivedStateOf {
                when (pageState.currentPage) {
                    0 -> Pair("", "Next")
                    1 -> Pair("Back", "Next")
                    2 -> Pair("Back", "Get Started")
                    else -> Pair("", "")
                }
            }
        }

        HorizontalPager(state = pageState) { index ->
            OnBoardingPage(page = pages[index])
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier,
                pageSize = pages.size,
                selectedPage = pageState.currentPage
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                val scope = rememberCoroutineScope()
                if (buttonState.value.first.isNotEmpty()) {
                    NewsTextButton(
                        text = buttonState.value.first,
                        onClick = {
                            scope.launch {
                                pageState.animateScrollToPage(page = pageState.currentPage - 1)
                            }
                        }
                    )
                }

                NewsButton(
                    text = buttonState.value.second,
                    onClick = {
                        scope.launch {
                            if (pageState.currentPage == pages.size - 1) {
                                event(OnBoardingEvent.SaveAppEntry)
                            } else {
                                pageState.animateScrollToPage(
                                    page = pageState.currentPage + 1
                                )
                            }
                        }
                    })
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}