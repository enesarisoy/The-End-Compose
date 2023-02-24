package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.ExternalContentType
import com.ns.theendcompose.data.model.ExternalId
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import javax.inject.Inject

class GetTvShowExternalIdsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): ApiResponse<List<ExternalId>> {
        val response = tvShowRepository.getExternalIds(tvShowId).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val externalIds = response.data?.toExternalIdList(type = ExternalContentType.Tv)
                ApiResponse.Success(externalIds)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }
    }
}