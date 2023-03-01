package com.ns.theendcompose.ui.screens.details.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ns.theendcompose.data.model.PersonDetails
import com.ns.theendcompose.ui.components.texts.ExpandableText
import com.ns.theendcompose.ui.theme.spacing

@Composable
fun PersonDetailsInfoSection(
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    text = details.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                if (details.biography.isNotEmpty()) {
                    ExpandableText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.medium),
                        text = details.biography
                    )
                }
            }
        }
    }

}