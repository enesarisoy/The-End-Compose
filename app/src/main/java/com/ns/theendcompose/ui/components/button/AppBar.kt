package com.ns.theendcompose.ui.components.button

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ns.theendcompose.ui.theme.spacing

@Composable
fun Appbar(
    title: String?,
    modifier: Modifier = Modifier,
    scrollState: ScrollState? = null,
    transparentScrollValueLimit: Float? = null,
    backgroundColor: Color = Color.Black,
    action: @Composable () -> Unit = {},
    trailing: @Composable () -> Unit = {}
) {
    val alphaDelta = 1f - backgroundColor.alpha

    val currentScrollValue = scrollState?.value

    val alpha = if (currentScrollValue != null && transparentScrollValueLimit != null) {
        (backgroundColor.alpha + (currentScrollValue / transparentScrollValueLimit) * alphaDelta).coerceIn(
            backgroundColor.alpha,
            1f
        )
    } else {
        backgroundColor.alpha
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        action()
        Spacer(modifier = modifier.width(MaterialTheme.spacing.small))
        title?.let {
            Text(
                text = it,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MaterialTheme.spacing.medium),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        trailing()
    }
}