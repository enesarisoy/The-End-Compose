package com.ns.theendcompose.domain.usecase.tvshow

import com.ns.theendcompose.data.model.tvshow.TvShowDetails
import com.ns.theendcompose.data.repository.browsed.RecentlyBrowsedRepository
import javax.inject.Inject

class AddRecentlyBrowsedTvShowUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) {
    operator fun invoke(details: TvShowDetails) {
        return recentlyBrowsedRepository.addRecentlyBrowsedTvShows(details)
    }
}