package com.srithar.cscmoi.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorAlertDialog(
    viewModel: CommonViewModel,
    onDismiss: () -> Unit
) {
    if (viewModel.errorMessage.isNotBlank()) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            },
            title = {
                Text("Error", color = MaterialTheme.colorScheme.error)
            },
            text = {
                Text(viewModel.errorMessage)
            }
        )
    }
}