package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.ProviderSource
import com.ns.theendcompose.data.repository.config.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllTvShowsWatchProvidersUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(): Flow<List<ProviderSource>> {
        return configRepository.getAllTvShowWatchProviders()
    }
}