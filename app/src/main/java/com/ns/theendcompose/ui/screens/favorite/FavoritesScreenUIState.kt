package com.ns.theendcompose.ui.screens.favorite

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ns.theendcompose.data.model.FavoriteType
import com.ns.theendcompose.data.model.Presentable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class FavoritesScreenUIState(
    val selectedFavoriteType: FavoriteType,
    val favorites: Flow<PagingData<Presentable>>
) {
    companion object {
        val default: FavoritesScreenUIState = FavoritesScreenUIState(
            selectedFavoriteType = FavoriteType.Movie,
            favorites = emptyFlow()
        )
    }
}