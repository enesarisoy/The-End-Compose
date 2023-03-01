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
import com.ns.theendcompose.data.model.PersonDetails
import com.ns.theendcompose.ui.components.texts.LabeledText
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.formatted

@Composable
fun PersonDetailsTopContent(
    personDetails: PersonDetails?,
    modifier: Modifier = Modifier
) {
    Crossfade(
        modifier = modifier,
        targetState = personDetails
    ) { details ->
        if (details != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                LabeledText(
                    label = stringResource(R.string.person_details_screen_know_for_label),
                    text = details.knownFor
                )

                details.birthPlace?.let { birthplace ->
                    LabeledText(
                        label = stringResource(R.string.person_details_screen_birthplace),
                        text = birthplace
                    )
                }

                details.birthday?.let { date ->
                    LabeledText(
                        label = stringResource(R.string.person_details_screen_birthday),
                        text = date.formatted()
                    )
                }

                details.deathDate?.let { date ->
                    LabeledText(
                        label = stringResource(R.string.person_details_screen_death_date),
                        text = date.formatted()
                    )
                }
            }
        }
    }
}