package com.ns.theendcompose.domain.usecase.tvshow

import androidx.paging.PagingData
import com.ns.theendcompose.data.model.tvshow.RecentlyBrowsedTvShow
import com.ns.theendcompose.data.repository.browsed.RecentlyBrowsedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentlyBrowsedTvShowsUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) {
    operator fun invoke(): Flow<PagingData<RecentlyBrowsedTvShow>> {
        return recentlyBrowsedRepository.recentlyBrowsedTvShows()
    }
}