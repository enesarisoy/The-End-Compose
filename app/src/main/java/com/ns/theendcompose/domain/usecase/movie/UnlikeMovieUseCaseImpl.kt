package com.ns.theendcompose.domain.usecase.movie

import com.ns.theendcompose.data.model.movie.MovieDetails
import com.ns.theendcompose.data.repository.favorites.FavoritesRepository
import javax.inject.Inject

class UnlikeMovieUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(details: MovieDetails) {
        return favoritesRepository.unlikeMovie(details)
    }
}