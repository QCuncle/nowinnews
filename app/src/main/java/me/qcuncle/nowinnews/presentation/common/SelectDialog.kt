package me.qcuncle.nowinnews.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable

@Composable
fun SelectDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,

    ) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = title,
        text = content,
        confirmButton = {
            NewsTextButton(
                text = "OK",
            ) {
                onDismiss()
                onConfirm()
            }
        },
    )
}