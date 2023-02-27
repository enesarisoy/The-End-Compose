package com.ns.theendcompose.ui.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ns.theendcompose.R

@Composable
fun ApplicationDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    infoText: String? = null,
    confirmButton: @Composable (() -> Unit)? = null,
    dismissButton: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        icon = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.height(50.dp)
            )
        },
        text = {
            infoText?.let { text -> Text(text = text) }
        },
        confirmButton = {
            confirmButton?.invoke()
        },
        dismissButton = {
            dismissButton?.invoke()
        }
    )
}