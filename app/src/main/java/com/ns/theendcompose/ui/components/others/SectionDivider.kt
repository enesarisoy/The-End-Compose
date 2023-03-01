package com.ns.theendcompose.ui.components.others

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SectionDivider(
    modifier: Modifier = Modifier
) {
    Divider(
        modifier = modifier,
        thickness = Dp.Hairline
    )
}