package com.ns.theendcompose.ui.components.others

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ns.theendcompose.utils.defaultPlaceHolder

@Composable
fun PosterPlaceHolder(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.defaultPlaceHolder())
}