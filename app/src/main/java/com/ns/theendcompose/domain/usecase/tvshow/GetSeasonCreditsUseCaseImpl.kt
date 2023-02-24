package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.AggregatedCredits
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.season.SeasonRepository
import javax.inject.Inject

class GetSeasonCreditsUseCaseImpl @Inject constructor(
    private val seasonRepository: SeasonRepository
) {
    suspend operator fun invoke(
        tvShowId: Int,
        seasonNumber: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<AggregatedCredits> {
        return seasonRepository.seasonCredits(
            tvShowId = tvShowId,
            seasonNumber = seasonNumber,
            isoCode = deviceLanguage.languageCode
        ).awaitApiResponse()
    }
}