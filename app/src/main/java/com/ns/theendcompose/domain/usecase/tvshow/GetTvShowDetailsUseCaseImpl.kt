package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.tvshow.TvShowDetails
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import javax.inject.Inject


class GetTvShowDetailsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(
        tvShowId: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<TvShowDetails> {
        return tvShowRepository.getTvShowDetails(
            tvShowId = tvShowId,
            deviceLanguage = deviceLanguage
        ).awaitApiResponse()
    }

}