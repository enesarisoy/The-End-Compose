package com.ns.theendcompose.ui.components.sections

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ns.theendcompose.data.model.WatchProviders
import com.ns.theendcompose.data.model.movie.MovieWatchProviderType
import com.ns.theendcompose.ui.components.button.MoviesWatchProvidersTypeButton
import com.ns.theendcompose.ui.components.chips.LogoChip
import com.ns.theendcompose.ui.components.texts.SectionLabel
import com.ns.theendcompose.ui.theme.spacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WatchProvidersSection(
    title: String,
    watchProviders: WatchProviders,
    modifier: Modifier = Modifier
) {
    val stream = watchProviders.flatrate?.sortedBy { provider ->
        provider.displayPriority
    } ?: emptyList()
    val rent = watchProviders.rent?.sortedBy { provider ->
        provider.displayPriority
    } ?: emptyList()
    val buy = watchProviders.buy?.sortedBy { provider ->
        provider.displayPriority
    } ?: emptyList()

    val availableTypes: List<MovieWatchProviderType> = buildList {
        if (stream.isNotEmpty()) add(MovieWatchProviderType.Stream)
        if (rent.isNotEmpty()) add(MovieWatchProviderType.Rent)
        if (buy.isNotEmpty()) add(MovieWatchProviderType.Buy)
    }
    var selectedType by remember(availableTypes) {
        mutableStateOf(availableTypes.firstOrNull())
    }

    val selectedProviders by remember(selectedType) {
        mutableStateOf(
            when (selectedType) {
                MovieWatchProviderType.Stream -> stream
                MovieWatchProviderType.Buy -> buy
                MovieWatchProviderType.Rent -> rent
                else -> null
            }
        )
    }

    if (availableTypes.isNotEmpty()){
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

                selectedType?.let { currentType ->
                    MoviesWatchProvidersTypeButton(
                        selectedType = currentType,
                        availableTypes = availableTypes
                    ) { type ->
                        selectedType = type
                    }
                }
            }
            LazyRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium)
            ) {
                selectedProviders?.let { providers ->
                    items(providers, key = { provider -> provider.providerId }) { provider ->
                        LogoChip(
                            modifier = Modifier.animateItemPlacement(),
                            logoPath = provider.logoPath,
                            text = provider.providerName
                        )
                    }
                }
            }
        }
    }
}