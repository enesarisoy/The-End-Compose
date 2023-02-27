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
fun ExitDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
) {
    ApplicationDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        infoText = stringResource(R.string.exit_dialog_info),
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick()
                }
            ) {
                Text(
                    stringResource(R.string.exit_dialog_confirm_button_label)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancelClick()
                }
            ) {
                Text(
                    stringResource(R.string.exit_dialog_cancel_button_label)
                )
            }
        },
    )
}