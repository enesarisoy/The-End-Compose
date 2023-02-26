package com.ns.theendcompose.ui.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ns.theendcompose.R

@Composable
@Preview
fun ExitDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        icon = { Icon(imageVector = Icons.Filled.Warning, contentDescription = null) },
        title = {
            Text(text = "Your about to exit the app")
        },
        text = {
            stringResource(id = R.string.exit_dialog_info)
        },
        confirmButton = {
            TextButton(onClick = onConfirmClick) {
                stringResource(id = R.string.exit_dialog_confirm_button_label)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelClick) {
                stringResource(id = R.string.exit_dialog_cancel_button_label)
            }
        }
    )
}