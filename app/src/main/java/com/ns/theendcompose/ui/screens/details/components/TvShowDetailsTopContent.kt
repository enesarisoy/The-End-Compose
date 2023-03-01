package com.ns.theendcompose.ui.screens.details.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ns.theendcompose.R
import com.ns.theendcompose.data.model.tvshow.TvShowDetails
import com.ns.theendcompose.ui.components.texts.LabeledText
import com.ns.theendcompose.ui.theme.spacing

@Composable
fun TvShowDetailsTopContent(
    tvShowDetails: TvShowDetails?,
    modifier: Modifier = Modifier
) {
    Crossfade(
        modifier = modifier,
        targetState = tvShowDetails
    ) { details ->
        if (details != null) {
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                tvShowDetails?.let { details ->
                    LabeledText(
                        label = stringResource(R.string.tv_series_details_type),
                        text = stringResource(details.type.getLabel())
                    )
                    LabeledText(
                        label = stringResource(R.string.tv_series_details_status),
                        text = stringResource(details.status.getLabel())
                    )

                    LabeledText(
                        label = stringResource(R.string.tv_series_details_in_production),
                        text = stringResource(if (details.inProduction) R.string.yes else R.string.no)
                    )
                }
            }
        }
    }
}