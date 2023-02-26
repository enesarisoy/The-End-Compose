package com.ns.theendcompose.domain.usecase.movie

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.GenresParam
import com.ns.theendcompose.data.model.WatchProvidersParam
import com.ns.theendcompose.data.model.movie.Movie
import com.ns.theendcompose.data.repository.movie.MovieRepository
import com.ns.theendcompose.ui.screens.discover.movies.MovieFilterState
import com.ns.theendcompose.ui.screens.discover.movies.SortInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDiscoverMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        sortInfo: SortInfo,
        filterState: MovieFilterState,
        deviceLanguage: DeviceLanguage
    ): Flow<PagingData<Movie>> {
        return movieRepository.discoverMovies(
            deviceLanguage = deviceLanguage,
            sortType = sortInfo.sortType,
            sortOrder = sortInfo.sortOrder,
            genresParam = GenresParam(filterState.selectedGenres),
            watchProvidersParam = WatchProvidersParam(filterState.selectedWatchProviders),
            voteRange = filterState.voteRange.current,
            onlyWithPosters = filterState.showOnlyWithPoster,
            onlyWithScore = filterState.showOnlyWithScore,
            onlyWithOverview = filterState.showOnlyWithOverview,
            releaseDateRange = filterState.releaseDateRange
        )
    }
}