package com.ns.theendcompose.domain.usecase.movie

import com.ns.theendcompose.data.model.Image
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieBackdropsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): ApiResponse<List<Image>> {
        val response = movieRepository.movieImages(movieId).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val backdrops = response.data?.backdrops ?: emptyList()
                ApiResponse.Success(backdrops)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }
    }
}