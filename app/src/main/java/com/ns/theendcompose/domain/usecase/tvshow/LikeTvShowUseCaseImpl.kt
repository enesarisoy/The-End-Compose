package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.tvshow.TvShowDetails
import com.ns.theendcompose.data.repository.favorites.FavoritesRepository
import javax.inject.Inject

class LikeTvShowUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(details: TvShowDetails) {
        return favoritesRepository.likeTvShow(details)
    }
}