package com.ns.theendcompose.ui.screens.browse.tvshows

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.domain.usecase.GetDeviceLanguageUseCaseImpl
import com.ns.theendcompose.domain.usecase.tvshow.ClearRecentlyBrowsedTvShowsUseCaseImpl
import com.ns.theendcompose.domain.usecase.tvshow.GetFavoriteTvShowsCountUseCaseImpl
import com.ns.theendcompose.domain.usecase.tvshow.GetTvShowOfTypeUseCaseImpl
import com.ns.theendcompose.ui.screens.destinations.BrowseTvShowsScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BrowseTvShowsViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCaseImpl,
    private val getFavoriteTvShowCountUseCase: GetFavoriteTvShowsCountUseCaseImpl,
    private val getTvShowOfTypeUseCase: GetTvShowOfTypeUseCaseImpl,
    private val clearRecentlyBrowsedTvShowUseCase: ClearRecentlyBrowsedTvShowsUseCaseImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val deviceLanguage: Flow<DeviceLanguage> = getDeviceLanguageUseCase()
    private val navArgs: BrowseTvShowsScreenArgs =
        BrowseTvShowsScreenDestination.argsFrom(savedStateHandle)

    private val favoriteTvShowCount: StateFlow<Int> = getFavoriteTvShowCountUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(10), 0)

    val uiState: StateFlow<BrowseTvShowsScreenUIState> = combine(
        deviceLanguage, favoriteTvShowCount
    ) { deviceLanguage, favoriteTvShowCount ->
        val tvShow = getTvShowOfTypeUseCase(
            type = navArgs.tvShowType,
            deviceLanguage = deviceLanguage
        ).cachedIn(viewModelScope)

        BrowseTvShowsScreenUIState(
            selectedTvShowType = navArgs.tvShowType,
            tvShow = tvShow,
            favoriteTvShowsCount = favoriteTvShowCount
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        BrowseTvShowsScreenUIState.getDefault(navArgs.tvShowType)
    )

    fun onClearClicked() = clearRecentlyBrowsedTvShowUseCase()
}