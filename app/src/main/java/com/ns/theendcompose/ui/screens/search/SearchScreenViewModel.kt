package com.ns.theendcompose.ui.screens.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ns.theendcompose.BaseViewModel
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.Presentable
import com.ns.theendcompose.data.model.SearchQuery
import com.ns.theendcompose.data.model.SearchResult
import com.ns.theendcompose.domain.usecase.*
import com.ns.theendcompose.domain.usecase.movie.GetPopularMoviesUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getDeviceLanguageUseCaseImpl: GetDeviceLanguageUseCaseImpl,
    private val getSpeechToTextAvailableUseCaseImpl: GetSpeechToTextAvailableUseCaseImpl,
    private val getCameraAvailableUseCaseImpl: GetCameraAvailableUseCaseImpl,
    private val mediaAddSearchQueryUseCaseImpl: MediaAddSearchQueryUseCaseImpl,
    private val mediaSearchQueriesUseCaseImpl: MediaSearchQueriesUseCaseImpl,
    private val getMediaMultiSearchUseCaseImpl: GetMediaMultiSearchUseCaseImpl,
    private val getPopularMoviesUseCaseImpl: GetPopularMoviesUseCaseImpl
) : BaseViewModel() {

    private val deviceLanguage: Flow<DeviceLanguage> = getDeviceLanguageUseCaseImpl()
    private val queryDelay = 500.milliseconds
    private val minQueryLength = 3

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val popularMovies: Flow<PagingData<Presentable>> =
        deviceLanguage.mapLatest { deviceLanguage ->
            getPopularMoviesUseCaseImpl(deviceLanguage)
        }.flattenMerge().cachedIn(viewModelScope)

    private val voiceSearchAvailable: Flow<Boolean> = getSpeechToTextAvailableUseCaseImpl()
    private val cameraSearchAvailable: Flow<Boolean> = getCameraAvailableUseCaseImpl()

    private val queryState: MutableStateFlow<QueryState> = MutableStateFlow(QueryState.default)
    private val suggestions: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val searchState: MutableStateFlow<SearchState> =
        MutableStateFlow(SearchState.EmptyQuery)
    private val resultState: MutableStateFlow<ResultState> =
        MutableStateFlow(ResultState.Default(popularMovies))
    private val queryLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val searchOptionsState: StateFlow<SearchOptionsState> = combine(
        voiceSearchAvailable, cameraSearchAvailable
    ) { voiceSearchAvailable, cameraSearchAvailable ->
        SearchOptionsState(
            voiceSearchAvailable = voiceSearchAvailable,
            cameraSearchAvailable = cameraSearchAvailable
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, SearchOptionsState.default)

    private var queryJob: Job? = null

    val uiState: StateFlow<SearchScreenUIState> = combine(
        searchOptionsState, queryState, suggestions, searchState, resultState
    ) { searchOptionsState, queryState, suggestions, searchState, resultState ->
        SearchScreenUIState(
            searchOptionsState,
            queryState.query,
            suggestions,
            searchState,
            resultState,
            queryState.loading
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, SearchScreenUIState.default)

    fun onQueryChange(queryText: String) {
        viewModelScope.launch {
            queryState.emit(queryState.value.copy(query = queryText))

            queryJob?.cancel()

            when {
                queryText.isBlank() -> {
                    searchState.emit(SearchState.EmptyQuery)
                    suggestions.emit(emptyList())
                    resultState.emit(ResultState.Default(popularMovies))
                }
                queryText.length < minQueryLength -> {
                    searchState.emit(SearchState.InsufficientQuery)
                    suggestions.emit(emptyList())
                }
                else -> {
                    val querySuggestions = mediaSearchQueriesUseCaseImpl(queryText)
                    suggestions.emit(querySuggestions)

                    queryJob = createQueryJob(queryText).apply{
                        start()
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun createQueryJob(query: String): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(queryDelay)

                queryLoading.emit(true)

                val searchResults = deviceLanguage.mapLatest { deviceLanguage ->
                    getMediaMultiSearchUseCaseImpl(
                        query = query,
                        deviceLanguage = deviceLanguage
                    )
                }.flattenMerge().cachedIn(viewModelScope)

                searchState.emit(SearchState.ValidQuery)
                resultState.emit(ResultState.Search(searchResults))
            } catch (_: CancellationException) {

            } finally {
                withContext(NonCancellable) {
                    queryLoading.emit(false)
                }
            }
        }
    }

    fun onQueryClear() {
        onQueryChange("")
    }

    fun onQuerySuggestionSelected(searchQuery: String) {
        if (queryState.value.query != searchQuery) {
            onQueryChange(searchQuery)
        }
    }

    fun addQuerySuggestion(searchQuery: SearchQuery) {
        mediaAddSearchQueryUseCaseImpl(searchQuery)
    }

    override fun onCleared() {
        super.onCleared()
        queryJob?.cancel()
    }
}


sealed class SearchState {
    object EmptyQuery : SearchState()
    object InsufficientQuery : SearchState()
    object ValidQuery : SearchState()
}

sealed class ResultState {
    data class Default(val popular: Flow<PagingData<Presentable>> = emptyFlow()) : ResultState()
    data class Search(val result: Flow<PagingData<SearchResult>>) : ResultState()
}

data class QueryState(
    val query: String?,
    val loading: Boolean
) {
    companion object {
        val default: QueryState = QueryState(
            query = null,
            loading = false
        )
    }
}
