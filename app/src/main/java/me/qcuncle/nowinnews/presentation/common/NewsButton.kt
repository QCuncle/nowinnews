package me.qcuncle.nowinnews.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.qcuncle.nowinnews.ui.theme.NinTheme

@Composable
fun NewsButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = 18.dp,
            vertical = 4.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(size = 32.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 2.dp, // 设置按钮的默认阴影高度
            pressedElevation = 8.dp // 设置按钮在被按下时的阴影高度
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun NewsCancelButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = 18.dp,
            vertical = 4.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        shape = RoundedCornerShape(size = 32.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 2.dp, // 设置按钮的默认阴影高度
            pressedElevation = 8.dp // 设置按钮在被按下时的阴影高度
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun NewsTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(size = 32.dp),
        modifier = Modifier.then(modifier)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun NewsCancelTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(size = 32.dp),
        colors = ButtonDefaults.textButtonColors().copy(
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun NewsTextButtonPreview() {
    NinTheme {
        Column {
            NewsTextButton(
                text = "TextButton",
                onClick = {}
            )

            NewsButton(
                text = "NewsButton",
                onClick = {}
            )

            NewsCancelTextButton(
                text = "TextButton",
                onClick = {}
            )

            NewsCancelButton(
                text = "NewsButton",
                onClick = {}
            )
        }
    }
}