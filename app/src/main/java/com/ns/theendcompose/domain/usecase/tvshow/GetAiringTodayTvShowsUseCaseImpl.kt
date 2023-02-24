package com.ns.theendcompose.domain.usecase.tvshow

import androidx.paging.PagingData
import androidx.paging.map
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.Presentable
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class GetAiringTodayTvShowsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(deviceLanguage: DeviceLanguage): Flow<PagingData<Presentable>> {
        return tvShowRepository.airingTodayTvShows(deviceLanguage)
            .mapLatest { data -> data.map { it } }
    }
}