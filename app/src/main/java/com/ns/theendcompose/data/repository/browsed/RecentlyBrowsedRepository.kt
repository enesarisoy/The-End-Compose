package com.ns.theendcompose.data.repository.browsed

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.movie.RecentlyBrowsedMovie
import com.ns.theendcompose.data.model.tvshow.RecentlyBrowsedTvShow
import com.ns.theendcompose.data.model.movie.MovieDetails
import com.ns.theendcompose.data.model.tvshow.TvShowDetails
import kotlinx.coroutines.flow.Flow

interface RecentlyBrowsedRepository {
    fun addRecentlyBrowsedMovie(movieDetails: MovieDetails)

    fun clearRecentlyBrowsedMovies()

    fun clearRecentlyBrowsedTvShows()

    fun recentlyBrowsedMovies(): Flow<PagingData<RecentlyBrowsedMovie>>

    fun addRecentlyBrowsedTvShows(tvShowDetails: TvShowDetails)

    fun recentlyBrowsedTvShows(): Flow<PagingData<RecentlyBrowsedTvShow>>
}