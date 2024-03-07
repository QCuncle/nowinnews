package me.qcuncle.nowinnews.presentation.navigator.compoments

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.ui.components.ThemedXmlDrawable
import me.qcuncle.nowinnews.ui.theme.NinTheme

@Composable
fun NinTopBar(
    title: String,
    onSearchClick: () -> Unit,
    onNodeClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

//        ThemedXmlDrawable(
//            drawableResId = R.drawable.baseline_search_24,
//            modifier = Modifier
//                .clip(CircleShape)
//                .clickable(onClick = onSearchClick)
//                .padding(horizontal = 16.dp, vertical = 6.dp)
//        )

        ThemedXmlDrawable(
            drawableResId = R.drawable.baseline_public_24,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onNodeClick)
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun NinTopBarPreview() {
    NinTheme {
        NinTopBar(
            title = "Now in news",
            onSearchClick = {},
            onNodeClick = {})
    }
}