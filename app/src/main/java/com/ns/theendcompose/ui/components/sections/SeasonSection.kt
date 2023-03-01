package com.ns.theendcompose.ui.components.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ns.theendcompose.data.model.PresentableItemState
import com.ns.theendcompose.data.model.Season
import com.ns.theendcompose.ui.components.items.PresentableItem
import com.ns.theendcompose.ui.components.texts.SectionLabel
import com.ns.theendcompose.ui.theme.spacing

@Composable
fun SeasonSection(
    title: String,
    seasons: List<Season>,
    modifier: Modifier = Modifier,
    onSeasonClick: (Int) -> Unit = {}
) {
    val state = rememberLazyListState()
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionLabel(
                modifier = Modifier.weight(1f),
                text = title
            )
        }
        Box {
            LazyRow(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium)
            ) {
                items(seasons) { season ->
                    PresentableItem(
                        presentableState = PresentableItemState.Result(season),
                        onClick = { onSeasonClick(season.seasonNumber) }
                    )
                }
            }
        }
    }
}