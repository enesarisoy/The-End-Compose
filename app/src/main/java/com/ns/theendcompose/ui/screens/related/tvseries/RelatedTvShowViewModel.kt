package com.ns.theendcompose.ui.screens.related.tvseries

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ns.theendcompose.BaseViewModel
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.domain.usecase.GetDeviceLanguageUseCaseImpl
import com.ns.theendcompose.domain.usecase.tvshow.GetRelatedTvShowsOfTypeUseCaseImpl
import com.ns.theendcompose.ui.screens.destinations.RelatedTvShowScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RelatedTvShowViewModel @Inject constructor(
    private val getDeviceLanguageUseCaseImpl: GetDeviceLanguageUseCaseImpl,
    private val getRelatedTvSeriesOfTypeUseCase: GetRelatedTvShowsOfTypeUseCaseImpl,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navArgs: RelatedTvShowScreenArgs =
        RelatedTvShowScreenDestination.argsFrom(savedStateHandle)
    private val deviceLanguage: Flow<DeviceLanguage> = getDeviceLanguageUseCaseImpl()

    val uiState: StateFlow<RelatedTvShowScreenUiState> =
        deviceLanguage.mapLatest { deviceLanguage ->
            val tvShow = getRelatedTvSeriesOfTypeUseCase(
                tvShowId = navArgs.tvShowId,
                type = navArgs.type,
                deviceLanguage = deviceLanguage
            ).cachedIn(viewModelScope)

            RelatedTvShowScreenUiState(
                relationType = navArgs.type,
                tvShow = tvShow,
                startRoute = navArgs.startRoute
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            RelatedTvShowScreenUiState.getDefault(navArgs.type)
        )
}