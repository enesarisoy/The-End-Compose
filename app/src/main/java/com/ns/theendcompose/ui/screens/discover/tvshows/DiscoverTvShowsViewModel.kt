package com.ns.theendcompose.ui.screens.discover.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.SortOrder
import com.ns.theendcompose.data.model.SortType
import com.ns.theendcompose.domain.usecase.GetDeviceLanguageUseCaseImpl
import com.ns.theendcompose.domain.usecase.tvshow.GetAllTvShowsWatchProvidersUseCaseImpl
import com.ns.theendcompose.domain.usecase.tvshow.GetDiscoverTvShowsUseCaseImpl
import com.ns.theendcompose.domain.usecase.tvshow.GetTvShowGenresUseCaseImpl
import com.ns.theendcompose.ui.screens.discover.movies.SortInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class DiscoverTvShowsViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCaseImpl,
    private val getTvShowGenresUseCase: GetTvShowGenresUseCaseImpl,
    private val getAllTvShowWatchProvidersUseCase: GetAllTvShowsWatchProvidersUseCaseImpl,
    private val getDiscoverTvShowUseCase: GetDiscoverTvShowsUseCaseImpl
) : ViewModel() {

    private val deviceLanguage: Flow<DeviceLanguage> = getDeviceLanguageUseCase()
    private val availableTvSeriesGenres = getTvShowGenresUseCase()
    private val availableWatchProviders = getAllTvShowWatchProvidersUseCase()

    private val sortInfo: MutableStateFlow<SortInfo> = MutableStateFlow(SortInfo.default)

    private val _filterState: MutableStateFlow<TvShowFilterState> =
        MutableStateFlow(TvShowFilterState.default)
    private val filterState: StateFlow<TvShowFilterState> = combine(
        _filterState,
        availableTvSeriesGenres,
        availableWatchProviders
    ) { filterState, genres, watchProviders ->
        filterState.copy(
            availableGenres = genres,
            availableWatchProviders = watchProviders
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, TvShowFilterState.default)

    val uiState: StateFlow<DiscoverTvShowsScreenUiState> = combine(
        deviceLanguage, sortInfo, filterState
    ) { deviceLanguage, sortInfo, filterState ->
        val tvShow = getDiscoverTvShowUseCase(
            sortInfo = sortInfo,
            filterState = filterState,
            deviceLanguage = deviceLanguage
        ).cachedIn(viewModelScope)

        DiscoverTvShowsScreenUiState(
            sortInfo = sortInfo,
            filterState = filterState,
            tvShow = tvShow
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        DiscoverTvShowsScreenUiState.default
    )

    fun onSortTypeChange(sortType: SortType) {
        viewModelScope.launch {
            val currentSortInfo = sortInfo.value

            sortInfo.emit(currentSortInfo.copy(sortType = sortType))
        }
    }

    fun onSortOrderChange(sortOrder: SortOrder) {
        viewModelScope.launch {
            val currentSortInfo = sortInfo.value

            sortInfo.emit(currentSortInfo.copy(sortOrder = sortOrder))
        }
    }

    fun onFilterStateChange(state: TvShowFilterState) {
        viewModelScope.launch {
            _filterState.emit(state)
        }
    }
}