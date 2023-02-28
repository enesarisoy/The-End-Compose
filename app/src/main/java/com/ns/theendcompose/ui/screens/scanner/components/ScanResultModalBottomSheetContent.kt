package com.ns.theendcompose.ui.screens.scanner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ns.theendcompose.R
import com.ns.theendcompose.ui.screens.scanner.ScanResult
import com.ns.theendcompose.ui.theme.spacing

@Composable
fun ModalBottomSheetContent(
    modifier: Modifier = Modifier,
    scanResult: ScanResult,
    onCloseClick: () -> Unit = {},
    onRejectedClicked: () -> Unit = {},
    onAcceptClicked: () -> Unit = {}
) {
    val scannedText = if (scanResult is ScanResult.Success) scanResult.text else ""

    Column(
        modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier.width(64.dp),
            color = Color.White.copy(0.3f),
            thickness = 4.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close filter",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(text = scannedText, fontSize = 32.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            Button(
                onClick = onAcceptClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                Text(text = stringResource(id = R.string.scanner_accept_button_label))
            }
            OutlinedButton(
                onClick = onRejectedClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                Text(text = stringResource(id = R.string.scanner_reject_button_label))
            }
        }
    }

}