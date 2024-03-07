package me.qcuncle.nowinnews.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
fun ThemedXmlDrawable(
    drawableResId: Int,
    iconColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier,
) {
    val context = LocalContext.current
    var xmlDrawable by remember(drawableResId) {
        mutableStateOf(context.getDrawable(drawableResId))
    }

    // 使用Modifier.drawWithContent和Modifier.graphicsLayer来应用颜色
    Image(
        painter = rememberDrawablePainter(xmlDrawable, iconColor),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
private fun rememberDrawablePainter(drawable: Drawable?, iconColor: Color): Painter {
    return remember(drawable, iconColor) {
        object : Painter() {
            override val intrinsicSize: Size
                get() = Size(
                    drawable?.intrinsicWidth?.toFloat() ?: 0f,
                    drawable?.intrinsicHeight?.toFloat() ?: 0f
                )

            override fun DrawScope.onDraw() {
                if (drawable != null) {
                    drawIntoCanvas { canvas ->
                        // 使用 iconColor 应用颜色
                        drawable.setTint(iconColor.toArgb())
                        drawable.setBounds(0, 0, size.width.toInt(), size.height.toInt())
                        drawable.draw(canvas.nativeCanvas)
                    }
                }
            }
        }
    }
}