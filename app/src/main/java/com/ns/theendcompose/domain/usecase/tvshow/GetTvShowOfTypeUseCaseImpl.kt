package com.ns.theendcompose.domain.usecase.tvshow

import androidx.paging.PagingData
import androidx.paging.map
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.Presentable
import com.ns.theendcompose.data.model.tvshow.TvShowType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class GetTvShowOfTypeUseCaseImpl @Inject constructor(
    private val getOnTheAirTvShowsUseCase: GetOnTheAirTvShowsUseCaseImpl,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCaseImpl,
    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCaseImpl,
    private val getTrendingTvShowsUseCase: GetTrendingTvShowsUseCaseImpl,
    private val getFavouriteTvShowsUseCase: GetFavoritesTvShowsUseCaseImpl,
    private val getRecentlyBrowsedTvShowsUseCase: GetRecentlyBrowsedTvShowsUseCaseImpl,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        type: TvShowType,
        deviceLanguage: DeviceLanguage
    ): Flow<PagingData<Presentable>> {
        return when (type) {
            TvShowType.OnTheAir -> getOnTheAirTvShowsUseCase(deviceLanguage, false)
            TvShowType.TopRated -> getTopRatedTvShowsUseCase(deviceLanguage)
            TvShowType.AiringToday -> getAiringTodayTvShowsUseCase(deviceLanguage)
            TvShowType.Trending -> getTrendingTvShowsUseCase(deviceLanguage)
            TvShowType.Favorite -> getFavouriteTvShowsUseCase()
            TvShowType.RecentlyBrowsed -> getRecentlyBrowsedTvShowsUseCase()
        }.mapLatest { data -> data.map { it } }
    }
}