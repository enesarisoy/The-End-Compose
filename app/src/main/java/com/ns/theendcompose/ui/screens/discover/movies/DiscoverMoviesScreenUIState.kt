package com.ns.theendcompose.ui.screens.discover.movies

import androidx.compose.runtime.Stable
import com.ns.theendcompose.data.model.*

@Stable
data class SortInfo(
    val sortType: SortType,
    val sortOrder: SortOrder
) {
    companion object {
        val default: SortInfo = SortInfo(
            sortType = SortType.Popularity,
            sortOrder = SortOrder.Desc
        )
    }
}

@Stable
data class MovieFilterState(
    val selectedGenres: List<Genre>,
    val availableGenres: List<Genre>,
    val selectedWatchProviders: List<ProviderSource>,
    val availableWatchProviders: List<ProviderSource>,
    val showOnlyWithPoster: Boolean,
    val showOnlyWithScore: Boolean,
    val showOnlyWithOverview: Boolean,
    val voteRange: VoteRange,
    val releaseDateRange: DateRange
) {
    companion object {
        val default: MovieFilterState = MovieFilterState(
            selectedGenres = emptyList(),
            availableGenres = emptyList(),
            availableWatchProviders = emptyList(),
            selectedWatchProviders = emptyList(),
            showOnlyWithPoster = false,
            showOnlyWithScore = false,
            showOnlyWithOverview = false,
            voteRange = VoteRange(),
            releaseDateRange = DateRange()
        )
    }

    fun clear(): MovieFilterState = copy(
        selectedGenres = emptyList(),
        selectedWatchProviders = emptyList(),
        showOnlyWithPoster = false,
        showOnlyWithScore = false,
        showOnlyWithOverview = false,
        voteRange = VoteRange(),
        releaseDateRange = DateRange()
    )
}