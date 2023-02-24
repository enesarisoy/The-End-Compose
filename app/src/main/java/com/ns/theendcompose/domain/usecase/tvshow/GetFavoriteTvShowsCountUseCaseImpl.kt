package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.repository.favorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteTvShowsCountUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<Int> {
        return favoritesRepository.getFavoriteTvShowsCount()
    }
}