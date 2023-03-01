package com.ns.theendcompose.ui.screens.details.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ns.theendcompose.BaseViewModel
import com.ns.theendcompose.data.model.*
import com.ns.theendcompose.data.remote.api.onException
import com.ns.theendcompose.data.remote.api.onFailure
import com.ns.theendcompose.data.remote.api.onSuccess
import com.ns.theendcompose.domain.usecase.GetCombinedCreditsUseCaseImpl
import com.ns.theendcompose.domain.usecase.GetDeviceLanguageUseCaseImpl
import com.ns.theendcompose.domain.usecase.GetPersonDetailsUseCaseImpl
import com.ns.theendcompose.domain.usecase.GetPersonExternalIdsUseCaseImpl
import com.ns.theendcompose.ui.screens.destinations.PersonDetailsScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCaseImpl,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCaseImpl,
    private val getCombinedCreditsUseCase: GetCombinedCreditsUseCaseImpl,
    private val getPersonExternalIdsUseCase: GetPersonExternalIdsUseCaseImpl,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navArgs: PersonDetailsScreenArgs =
        PersonDetailsScreenDestination.argsFrom(savedStateHandle)
    private val deviceLanguage: Flow<DeviceLanguage> = getDeviceLanguageUseCase()

    private val personDetails: MutableStateFlow<PersonDetails?> = MutableStateFlow(null)
    private val combinedCredits: MutableStateFlow<CombinedCredits?> = MutableStateFlow(null)

    private val _externalIds: MutableStateFlow<ExternalIds?> = MutableStateFlow(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val externalIds: StateFlow<List<ExternalId>?> =
        _externalIds.filterNotNull().mapLatest { externalIds ->
            externalIds.toExternalIdList(type = ExternalContentType.Person)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10), null)

    val uiState: StateFlow<PersonDetailsScreenUIState> = combine(
        personDetails, combinedCredits, externalIds, error
    ) { details, combinedCredits, externalIds, error ->
        PersonDetailsScreenUIState(
            startRoute = navArgs.startRoute,
            details = details,
            externalIds = externalIds,
            credits = combinedCredits,
            error = error
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        PersonDetailsScreenUIState.getDefault(navArgs.startRoute)
    )

    init {
        getPersonInfo()
    }

    private fun getPersonInfo() {
        viewModelScope.launch {
            deviceLanguage.collectLatest { deviceLanguage ->
                launch {
                    getPersonDetails(
                        personId = navArgs.personId,
                        deviceLanguage = deviceLanguage
                    )
                }

                launch {
                    getCombinedCredits(
                        personId = navArgs.personId,
                        deviceLanguage = deviceLanguage
                    )
                }

                launch {
                    getExternalIds(
                        personId = navArgs.personId,
                        deviceLanguage = deviceLanguage
                    )
                }
            }
        }
    }

    private suspend fun getPersonDetails(personId: Int, deviceLanguage: DeviceLanguage) {
        getPersonDetailsUseCase(
            personId = personId, deviceLanguage = deviceLanguage
        ).onSuccess {
            viewModelScope.launch {
                personDetails.emit(data)
            }
        }.onFailure {
            onFailure(this)
        }.onException {
            onError(this)
        }
    }


    private suspend fun getCombinedCredits(personId: Int, deviceLanguage: DeviceLanguage) {
        getCombinedCreditsUseCase(
            personId = personId,
            deviceLanguage = deviceLanguage
        ).onSuccess {
            viewModelScope.launch {
                combinedCredits.emit(data)
            }
        }.onFailure {
            onFailure(this)
        }.onException {
            onError(this)
        }
    }

    private suspend fun getExternalIds(personId: Int, deviceLanguage: DeviceLanguage) {
        getPersonExternalIdsUseCase(
            personId = personId,
            deviceLanguage = deviceLanguage
        ).onSuccess {
            viewModelScope.launch {
                _externalIds.emit(data)
            }
        }.onFailure {
            onFailure(this)
        }.onException {
            onError(this)
        }
    }
}