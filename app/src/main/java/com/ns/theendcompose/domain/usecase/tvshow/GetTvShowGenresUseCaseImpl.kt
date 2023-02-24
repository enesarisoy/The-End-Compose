package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.Genre
import com.ns.theendcompose.data.repository.config.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetTvShowGenresUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(): Flow<List<Genre>> {
        return configRepository.getTvShowGenres()
    }
}