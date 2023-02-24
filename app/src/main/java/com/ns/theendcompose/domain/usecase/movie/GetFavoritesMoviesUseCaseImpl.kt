package com.ns.theendcompose.domain.usecase.movie

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.movie.MovieFavorite
import com.ns.theendcompose.data.repository.favorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesMoviesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<PagingData<MovieFavorite>> {
        return favoritesRepository.favoriteMovies()
    }
}