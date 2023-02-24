package com.ns.theendcompose.domain.usecase.tvshow

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.GenresParam
import com.ns.theendcompose.data.model.WatchProvidersParam
import com.ns.theendcompose.data.model.tvshow.TvShow
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import com.ns.theendcompose.ui.screens.discover.movies.SortInfo
import com.ns.theendcompose.ui.screens.discover.tvshows.TvShowFilterState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDiscoverTvShowsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    operator fun invoke(
        sortInfo: SortInfo,
        filterState: TvShowFilterState,
        deviceLanguage: DeviceLanguage
    ): Flow<PagingData<TvShow>> {
        return tvShowRepository.discoverTvShows(
            deviceLanguage = deviceLanguage,
            sortType = sortInfo.sortType,
            sortOrder = sortInfo.sortOrder,
            genresParam = GenresParam(filterState.selectedGenres),
            watchProvidersParam = WatchProvidersParam(filterState.selectedWatchProviders),
            voteRange = filterState.voteRange.current,
            onlyWithPosters = filterState.showOnlyWithPoster,
            onlyWithScore = filterState.showOnlyWithScore,
            onlyWithOverview = filterState.showOnlyWithOverview,
            airDateRange = filterState.airDateRange
        )
    }
}