package me.qcuncle.nowinnews.presentation.subscription.compoments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class SubscriptionStatus {
    IDLE,
    SUBSCRIBING,
    SUCCESS,
    FAILED
}

@Composable
fun SubscriptionDialog(
    status: SubscriptionStatus,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .width(200.dp)
            .height(70.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.98f),
                shape = RoundedCornerShape(16.dp)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (status) {
            SubscriptionStatus.SUBSCRIBING -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(36.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            SubscriptionStatus.SUCCESS -> {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            SubscriptionStatus.FAILED -> {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }

            else -> {}
        }

        Spacer(modifier = Modifier.size(16.dp))

        when (status) {
            SubscriptionStatus.SUBSCRIBING -> {
                Text("订阅中...", style = MaterialTheme.typography.bodyMedium)
            }

            SubscriptionStatus.SUCCESS -> {
                Text("订阅成功", style = MaterialTheme.typography.bodyMedium)
            }

            SubscriptionStatus.FAILED -> {
                Text("订阅失败", style = MaterialTheme.typography.bodyMedium)
            }

            else -> {}
        }
    }
}
