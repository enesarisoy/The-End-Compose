package com.ns.theendcompose.domain.usecase.movie

import com.ns.theendcompose.data.model.ExternalContentType
import com.ns.theendcompose.data.model.ExternalId
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.movie.MovieRepository
import javax.inject.Inject


class GetMovieExternalIdsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): ApiResponse<List<ExternalId>> {
        val response = movieRepository.getExternalIds(movieId).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val ids = response.data?.toExternalIdList(ExternalContentType.Movie)
                ApiResponse.Success(ids)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }
    }
}