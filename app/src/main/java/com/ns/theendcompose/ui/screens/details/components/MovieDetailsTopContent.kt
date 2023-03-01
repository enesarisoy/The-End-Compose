package com.ns.theendcompose.ui.screens.details.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ns.theendcompose.R
import com.ns.theendcompose.data.model.movie.MovieDetails
import com.ns.theendcompose.ui.components.texts.LabeledText
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.formattedMoney
import org.threeten.bp.Duration

@Composable
fun MovieDetailsTopContent(
    movieDetails: MovieDetails?,
    modifier: Modifier = Modifier
) {
    Crossfade(
        modifier = modifier,
        targetState = movieDetails
    ) { details ->
        if (details != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                LabeledText(
                    label = stringResource(R.string.movie_details_status),
                    text = stringResource(details.status.getLabel())
                )

                if (details.budget > 0) {
                    LabeledText(
                        label = stringResource(R.string.movie_details_budget),
                        text = details.budget.formattedMoney()
                    )
                }
                if (details.revenue > 0) {
                    LabeledText(
                        label = stringResource(R.string.movie_details_boxoffice),
                        text = details.revenue.formattedMoney()
                    )
                }
            }
        }
    }
}