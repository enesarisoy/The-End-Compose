package com.ns.theendcompose.ui.screens.related.tvseries

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ns.theendcompose.data.model.RelationType
import com.ns.theendcompose.data.model.tvshow.TvShow
import com.ns.theendcompose.ui.screens.destinations.MovieScreenDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class RelatedTvShowScreenUiState(
    val relationType: RelationType,
    val tvShow: Flow<PagingData<TvShow>>,
    val startRoute: String
) {
    companion object {
        fun getDefault(relationType: RelationType): RelatedTvShowScreenUiState {
            return RelatedTvShowScreenUiState(
                relationType = relationType,
                tvShow = emptyFlow(),
                startRoute = MovieScreenDestination.route
            )
        }
    }
}