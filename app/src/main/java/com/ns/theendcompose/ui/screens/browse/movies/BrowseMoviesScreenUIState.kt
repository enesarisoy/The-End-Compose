package com.ns.theendcompose.ui.screens.browse.movies

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ns.theendcompose.data.model.Presentable
import com.ns.theendcompose.data.model.movie.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class BrowseMoviesScreenUIState(
    val selectedMovieType: MovieType,
    val movies: Flow<PagingData<Presentable>>,
    val favoriteMoviesCount: Int
) {
    companion object {
        fun getDefault(selectedMovieType: MovieType): BrowseMoviesScreenUIState {
            return BrowseMoviesScreenUIState(
                selectedMovieType = selectedMovieType,
                movies = emptyFlow(),
                favoriteMoviesCount = 0
            )
        }
    }
}