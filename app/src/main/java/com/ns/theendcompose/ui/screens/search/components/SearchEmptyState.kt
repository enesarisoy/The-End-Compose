package com.ns.theendcompose.ui.screens.search.components

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.ns.theendcompose.R
import com.ns.theendcompose.ui.theme.spacing

@Preview
@Composable
fun SearchEmptyState(
    modifier: Modifier = Modifier,
    onEditButtonClicked: () -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_search))
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = PorterDuffColorFilter(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f).toArgb(),
                PorterDuff.Mode.SRC_ATOP
            ),
            keyPath = arrayOf("**")
        )
    )

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(160.dp),
            speed = 0.2f,
            iterations = LottieConstants.IterateForever,
            dynamicProperties = dynamicProperties
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Text(text = stringResource(id = R.string.search_empty_state))
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        OutlinedButton(onClick = onEditButtonClicked) {
            Text(text = stringResource(id = R.string.search_empty_state_edit_button_label))
        }
    }

}