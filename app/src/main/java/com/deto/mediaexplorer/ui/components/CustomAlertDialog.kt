package com.deto.mediaexplorer.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.deto.mediaexplorer.R

@Composable
fun CustomAlertDialog(showDialog: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit, title: String, text: String) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(title) },
            text = { Text(text) },
            confirmButton = {
                TextButton(
                    onClick = { onConfirm() }
                ) {
                    Text(stringResource(R.string.alertdialog_confirmbutton))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.alertdialog_dismissbutton))
                }
            }
        )
    }
}