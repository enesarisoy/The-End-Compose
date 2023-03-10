package com.ns.theendcompose.ui.components.texts

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun AdditionalInfoText(
    infoTexts: List<String>,
    modifier: Modifier = Modifier
) {
    val text = infoTexts.joinToString(separator = " . ")

    Text(
        text = text,
        modifier = modifier,
//        color = Color.White.copy(0.5f),
//        fontSize = 12.sp
    )
}