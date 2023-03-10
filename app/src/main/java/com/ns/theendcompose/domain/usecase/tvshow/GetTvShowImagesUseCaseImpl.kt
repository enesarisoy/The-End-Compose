package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.Image
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import javax.inject.Inject

class GetTvShowImagesUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): ApiResponse<List<Image>> {
        val response = tvShowRepository.tvShowImages(tvShowId).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val images = response.data?.backdrops ?: emptyList()
                ApiResponse.Success(images)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }
    }
}