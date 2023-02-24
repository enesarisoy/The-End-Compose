package com.ns.theendcompose.domain.usecase.movie

import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.WatchProviders
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieWatchProvidersUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<WatchProviders?> {
        val response = movieRepository.watchProviders(
            movieId = movieId
        ).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val results = response.data?.results
                val watchProviders = results?.getOrElse(deviceLanguage.region) { null }
                ApiResponse.Success(watchProviders)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }
    }

}