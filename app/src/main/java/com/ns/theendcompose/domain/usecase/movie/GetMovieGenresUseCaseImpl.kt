package com.ns.theendcompose.domain.usecase.movie

import com.ns.theendcompose.data.model.Genre
import com.ns.theendcompose.data.repository.config.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMovieGenresUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(): Flow<List<Genre>> {
        return configRepository.getMoviesGenres()
    }
}