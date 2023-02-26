package com.ns.theendcompose.ui.screens.discover.tvshows

import androidx.compose.runtime.Stable
import com.ns.theendcompose.data.model.DateRange
import com.ns.theendcompose.data.model.Genre
import com.ns.theendcompose.data.model.ProviderSource
import com.ns.theendcompose.data.model.VoteRange

@Stable
data class TvShowFilterState(
    val selectedGenres: List<Genre>,
    val availableGenres: List<Genre> = emptyList(),
    val selectedWatchProviders: List<ProviderSource>,
    val availableWatchProviders: List<ProviderSource>,
    val showOnlyWithPoster: Boolean,
    val showOnlyWithScore: Boolean,
    val showOnlyWithOverview: Boolean,
    val voteRange: VoteRange = VoteRange(),
    val airDateRange: DateRange = DateRange()
) {
    companion object {
        val default: TvShowFilterState = TvShowFilterState(
            selectedGenres = emptyList(),
            availableGenres = emptyList(),
            selectedWatchProviders = emptyList(),
            availableWatchProviders = emptyList(),
            showOnlyWithPoster = false,
            showOnlyWithScore = false,
            showOnlyWithOverview = false,
            voteRange = VoteRange(),
            airDateRange = DateRange()
        )
    }

    fun clear(): TvShowFilterState = copy(
        selectedGenres = emptyList(),
        selectedWatchProviders = emptyList(),
        showOnlyWithPoster = false,
        showOnlyWithScore = false,
        showOnlyWithOverview = false,
        voteRange = VoteRange(),
        airDateRange = DateRange()
    )
}