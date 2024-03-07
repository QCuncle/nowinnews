package me.qcuncle.nowinnews.presentation.common

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.qcuncle.nowinnews.R
import me.qcuncle.nowinnews.ui.components.ThemedXmlDrawable
import me.qcuncle.nowinnews.ui.theme.NinTheme

@Composable
fun SettingItem(
    @DrawableRes icon: Int,
    title: String,
    content: String,
    contentTextColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        ThemedXmlDrawable(
            drawableResId = icon,
            iconColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                lineHeight = 18.sp
            )

            Text(
                text = content,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                color = contentTextColor
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun NewsTextButtonPreview() {
    NinTheme {
        Surface {
            Column {
                SettingItem(
                    icon = R.drawable.baseline_public_24,
                    title = "收藏帖子",
                    content = "128",
                    onClick = {})
            }
        }
    }
}