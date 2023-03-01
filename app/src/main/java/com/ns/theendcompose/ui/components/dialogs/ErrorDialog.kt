package com.ns.theendcompose.ui.components.dialogs

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ns.theendcompose.R

@Preview
@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    errorMessage: String? = null,
    onConfirmClick: () -> Unit = {}
) {
    ApplicationDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        infoText = errorMessage ?: stringResource(id = R.string.error_dialog_default_text),
        confirmButton = {
            OutlinedButton(onClick = onConfirmClick) {
                Text(text = stringResource(id = R.string.exit_dialog_confirm_button_label))
            }
        }
    )
}