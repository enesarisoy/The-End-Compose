package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.WatchProviders
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import javax.inject.Inject

class GetTvShowWatchProvidersUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(
        tvShowId: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<WatchProviders?> {
        val response = tvShowRepository.watchProviders(
            tvShowId = tvShowId
        ).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val results = response.data?.results
                val providers = results?.getOrElse(deviceLanguage.region) { null }

                ApiResponse.Success(providers)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }
    }

}