package com.ns.theendcompose.ui.components.sections

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowRow
import com.ns.theendcompose.data.model.Genre
import com.ns.theendcompose.ui.components.chips.GenreChip
import com.ns.theendcompose.ui.theme.spacing

@Composable
fun GenresSection(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        mainAxisSpacing = MaterialTheme.spacing.extraSmall,
        crossAxisSpacing = MaterialTheme.spacing.extraSmall
    ) {
        genres.map { genre ->
            GenreChip(text = genre.name)
        }
    }
}