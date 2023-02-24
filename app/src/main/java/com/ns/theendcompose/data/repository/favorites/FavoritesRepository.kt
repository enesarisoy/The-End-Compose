package com.ns.theendcompose.data.repository.favorites

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.movie.MovieDetails
import com.ns.theendcompose.data.model.movie.MovieFavorite
import com.ns.theendcompose.data.model.tvshow.TvShowDetails
import com.ns.theendcompose.data.model.tvshow.TvShowFavorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun likeMovie(movieDetails: MovieDetails)

    fun likeTvShow(tvShowDetails: TvShowDetails)

    fun unlikeMovie(movieDetails: MovieDetails)

    fun unlikeTvShows(tvShowDetails: TvShowDetails)

    fun favoriteMovies(): Flow<PagingData<MovieFavorite>>

    fun favoriteTvShows(): Flow<PagingData<TvShowFavorite>>

    fun getFavoriteMoviesIds(): Flow<List<Int>>

    fun getFavoriteTvShowsIds(): Flow<List<Int>>

    fun getFavoriteMoviesCount(): Flow<Int>

    fun getFavoriteTvShowsCount(): Flow<Int>
}