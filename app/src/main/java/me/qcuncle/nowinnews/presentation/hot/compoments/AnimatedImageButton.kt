package me.qcuncle.nowinnews.presentation.hot.compoments

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import me.qcuncle.nowinnews.ui.components.ThemedXmlDrawable

@Composable
fun RotateAnimatedImageButton(
    @DrawableRes drawableId: Int,
    rotationDegree: Float,
    direction: Direction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    var rotationState by remember { mutableFloatStateOf(0f) }
    var isAnimationRunning by remember { mutableStateOf(false) }

    val rotationAnimation by animateFloatAsState(
        targetValue = if (direction == Direction.CLOCKWISE) {
            if (rotationState == 0f) 0f else rotationDegree
        } else if (direction == Direction.COUNTERCLOCKWISE) {
            if (rotationState == 0f) rotationDegree else 0f
        } else {
            if (rotationState == 0f) 0f else rotationDegree
        },
        animationSpec = tween(500),
        finishedListener = {
            rotationState = 0f
            isAnimationRunning = false
        }, label = ""
    )

    ThemedXmlDrawable(
        drawableResId = drawableId,
        modifier = modifier
            .clickable {
                onClick()
                rotationState += rotationDegree
                isAnimationRunning = true
            }
            .padding(4.dp)
            .rotate(rotationAnimation),
        iconColor = MaterialTheme.colorScheme.primary
    )
}

enum class Direction {
    CLOCKWISE, COUNTERCLOCKWISE
}
