package me.qcuncle.nowinnews.presentation.hot.compoments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CircularProgressIndicatorWithTimeout() {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.9f))
    ) {
        // Show CircularProgressIndicator while loading
        CircularProgressIndicator(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.Center),
            strokeWidth = 3.dp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}