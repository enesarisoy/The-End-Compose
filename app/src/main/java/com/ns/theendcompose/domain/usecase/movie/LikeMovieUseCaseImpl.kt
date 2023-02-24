package com.ns.theendcompose.domain.usecase.movie

import com.ns.theendcompose.data.model.movie.MovieDetails
import com.ns.theendcompose.data.repository.favorites.FavoritesRepository
import javax.inject.Inject

class LikeMovieUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(details: MovieDetails) {
        return favoritesRepository.likeMovie(details)
    }
}