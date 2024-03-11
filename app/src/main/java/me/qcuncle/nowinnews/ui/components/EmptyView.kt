package me.qcuncle.nowinnews.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun EmptyView(
    @DrawableRes iconResId: Int,
    text: AnnotatedString,
    percentage: Float = 1f,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(percentage)
    ) {
        Image(
            contentDescription = null,
            contentScale = ContentScale.Inside,
            painter = painterResource(iconResId),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun EmptyView(
    @DrawableRes iconResId: Int,
    text: CharSequence,
    percentage: Float = 1f,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(percentage)
    ) {
        Image(
            contentDescription = null,
            contentScale = ContentScale.Inside,
            painter = painterResource(iconResId),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = text.toString(),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}