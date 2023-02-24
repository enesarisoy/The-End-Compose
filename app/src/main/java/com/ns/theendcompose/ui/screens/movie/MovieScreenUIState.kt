package com.ns.theendcompose.ui.screens.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ns.theendcompose.data.model.DetailPresentable
import com.ns.theendcompose.data.model.Presentable
import com.ns.theendcompose.data.model.movie.MovieFavorite
import com.ns.theendcompose.data.model.movie.RecentlyBrowsedMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenUIState(
    val moviesState: MoviesState,
    val favorites: Flow<PagingData<MovieFavorite>>,
    val recentlyBrowsed: Flow<PagingData<RecentlyBrowsedMovie>>
) {

    companion object {
        val default: MovieScreenUIState = MovieScreenUIState(
            moviesState = MoviesState.default,
            favorites = emptyFlow(),
            recentlyBrowsed = emptyFlow()
        )
    }
}

@Stable
data class MoviesState(
    val discover: Flow<PagingData<Presentable>>,
    val upcoming: Flow<PagingData<Presentable>>,
    val topRated: Flow<PagingData<Presentable>>,
    val trending: Flow<PagingData<Presentable>>,
    val nowPlaying: Flow<PagingData<DetailPresentable>>,
){
    companion object {
        val default: MoviesState = MoviesState(
            discover = emptyFlow(),
            upcoming = emptyFlow(),
            topRated = emptyFlow(),
            trending = emptyFlow(),
            nowPlaying = emptyFlow()
        )
    }
}
