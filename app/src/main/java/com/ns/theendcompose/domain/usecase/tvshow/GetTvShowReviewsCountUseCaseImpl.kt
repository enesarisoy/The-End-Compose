package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import javax.inject.Inject

class GetTvShowReviewsCountUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): ApiResponse<Int> {
        val response = tvShowRepository.tvShowReview(tvShowId).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val reviewCount = response.data?.totalResults ?: 0
                ApiResponse.Success(reviewCount)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }
    }
}