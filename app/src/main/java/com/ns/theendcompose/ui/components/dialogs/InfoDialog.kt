package com.ns.theendcompose.ui.components.dialogs


import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ns.theendcompose.R

@Composable
fun InfoDialog(
    infoText: String?,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    ApplicationDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        infoText = infoText,
        confirmButton = {
            TextButton(
                onClick = onConfirmClick
            ) {
                Text(text = stringResource(R.string.exit_dialog_confirm_button_label))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelClick
            ) {
                Text(text = stringResource(R.string.exit_dialog_cancel_button_label))
            }
        }
    )
}