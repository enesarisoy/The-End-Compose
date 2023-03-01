package com.ns.theendcompose.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ns.theendcompose.data.model.FavoriteType
import com.ns.theendcompose.domain.usecase.GetFavoritesUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val getFavoritesUseCaseImpl: GetFavoritesUseCaseImpl
) : ViewModel() {
    private val _selectedFavoriteType: MutableStateFlow<FavoriteType> =
        MutableStateFlow(FavoriteType.Movie)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<FavoritesScreenUIState> = _selectedFavoriteType.mapLatest { type ->
        val favorites = getFavoritesUseCaseImpl(type).cachedIn(viewModelScope)

        FavoritesScreenUIState(
            selectedFavoriteType = type,
            favorites = favorites
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, FavoritesScreenUIState.default)

    fun onFavoriteTypeSelected(type: FavoriteType) {
        viewModelScope.launch {
            _selectedFavoriteType.emit(type)
        }
    }
}