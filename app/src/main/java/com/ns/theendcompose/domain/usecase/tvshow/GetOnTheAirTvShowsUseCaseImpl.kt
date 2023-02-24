package com.ns.theendcompose.domain.usecase.tvshow

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.ns.theendcompose.data.model.DetailPresentable
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.tvshow.TvShowDetailEntity
import com.ns.theendcompose.data.repository.tvshow.TvShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class GetOnTheAirTvShowsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        deviceLanguage: DeviceLanguage,
        filtered: Boolean
    ): Flow<PagingData<DetailPresentable>> {
        return tvShowRepository.onTheAirTvShows(
            deviceLanguage
        ).mapLatest { data ->
            if (filtered) data.filterCompleteInfo() else data
        }.mapLatest { data -> data.map { it } }
    }

    private fun PagingData<TvShowDetailEntity>.filterCompleteInfo(): PagingData<TvShowDetailEntity> {
        return filter { tvShow ->
            tvShow.run {
                !backdropPath.isNullOrEmpty() &&
                        !posterPath.isNullOrEmpty() &&
                        title.isNotEmpty() &&
                        overview.isNotEmpty()
            }
        }
    }
}