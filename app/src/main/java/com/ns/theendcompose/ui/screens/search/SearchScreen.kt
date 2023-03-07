package com.ns.theendcompose.ui.screens.search

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.ns.theendcompose.R
import com.ns.theendcompose.data.model.MediaType
import com.ns.theendcompose.data.model.SearchQuery
import com.ns.theendcompose.ui.components.sections.PresentableGridSection
import com.ns.theendcompose.ui.components.sections.SearchGridSection
import com.ns.theendcompose.ui.screens.destinations.MovieDetailsScreenDestination
import com.ns.theendcompose.ui.screens.destinations.ScannerScreenDestination
import com.ns.theendcompose.ui.screens.destinations.SearchScreenDestination
import com.ns.theendcompose.ui.screens.destinations.TvShowDetailsScreenDestination
import com.ns.theendcompose.ui.screens.search.components.QueryTextField
import com.ns.theendcompose.ui.screens.search.components.SearchEmptyState
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.CaptureSpeechToText
import com.ns.theendcompose.utils.isNotEmpty
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import java.util.*

@OptIn(ExperimentalLifecycleComposeApi::class)
@Destination
@Composable
fun AnimatedVisibilityScope.SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<ScannerScreenDestination, String>

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onQueryChanged: (query: String) -> Unit = viewModel::onQueryChange
    val onQueryCleared: () -> Unit = viewModel::onQueryClear
    val onAddSearchQuerySuggestions: (SearchQuery) -> Unit = viewModel::addQuerySuggestion

    val onCameraClicked = {
        navigator.navigate(ScannerScreenDestination)
    }
    val onResultClicked: (id: Int, type: MediaType) -> Unit = { id, type ->
        val destination = when (type) {
            MediaType.Movie -> {
                MovieDetailsScreenDestination(
                    movieId = id,
                    startRoute = SearchScreenDestination.route
                )
            }

            MediaType.Tv -> {
                TvShowDetailsScreenDestination(
                    tvShowId = id,
                    startRoute = SearchScreenDestination.route
                )
            }

            else -> null
        }

        if (destination != null) {
            val searchQuery = SearchQuery(
                query = uiState.query.orEmpty(),
                lastUseDate = Date()
            )
            onAddSearchQuerySuggestions(searchQuery)

            navigator.navigate(destination)
        }
    }
    val onMovieClicked = { movieId: Int ->
        val destination = MovieDetailsScreenDestination(
            movieId = movieId,
            startRoute = SearchScreenDestination.route
        )

        navigator.navigate(destination)
    }
    val onQuerySuggestionSelected: (String) -> Unit = viewModel::onQuerySuggestionSelected
    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Value -> {
                viewModel.onQueryChange(result.value)
            }
            else -> Unit
        }
    }
    SearchScreenContent(
        uiState = uiState,
        onQueryChanged = onQueryChanged,
        onQueryCleared = onQueryCleared,
        onQuerySuggestionSelected = onQuerySuggestionSelected,
        onCameraClicked = onCameraClicked,
        onResultClicked = onResultClicked,
        onMovieClicked = onMovieClicked
    )
}

@Composable
fun SearchScreenContent(
    uiState: SearchScreenUIState,
    onQueryChanged: (query: String) -> Unit,
    onQueryCleared: () -> Unit,
    onResultClicked: (id: Int, type: MediaType) -> Unit,
    onCameraClicked: () -> Unit = {},
    onMovieClicked: (Int) -> Unit,
    onQuerySuggestionSelected: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val queryTextFieldFocusRequester = remember {
        FocusRequester()
    }
    val clearFocus = { focusManager.clearFocus(force = true) }
    val speechToTextLauncher = rememberLauncherForActivityResult(CaptureSpeechToText()) { result ->
        if (result != null) {
            focusManager.clearFocus()
            onQueryChanged(result)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        QueryTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.extraSmall)
                .animateContentSize(),
            query = uiState.query,
            suggestions = uiState.suggestions,
            voiceSearchAvailable = uiState.searchOptionsState.voiceSearchAvailable,
            cameraSearchAvailable = uiState.searchOptionsState.cameraSearchAvailable,
            loading = uiState.queryLoading,
            showClearButton = uiState.searchState !is SearchState.EmptyQuery,
            focusRequester = queryTextFieldFocusRequester,
            info = {
                AnimatedVisibility(
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically(),
                    visible = uiState.searchState is SearchState.InsufficientQuery
                ) {
                    Text(text = stringResource(R.string.search_insufficient_query_length_info_text))
                }
            },
            onKeyboardSearchClicked = {
                clearFocus()
            },
            onQueryChange = onQueryChanged,
            onQueryClear = {
                onQueryCleared()
                queryTextFieldFocusRequester.requestFocus()
            },
            onVoiceSearchClick = {
                speechToTextLauncher.launch(null)
            },
            onCameraSearchClick = onCameraClicked,
            onSuggestionClick = { suggestion ->
                clearFocus()
                onQuerySuggestionSelected(suggestion)
            }
        )
        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = uiState.resultState
        ) { state ->
            when (state) {
                is ResultState.Default -> {
                    val popular = state.popular.collectAsLazyPagingItems()

                    if (popular.isNotEmpty()) {
                        PresentableGridSection(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = MaterialTheme.spacing.medium,
                                start = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.small,
                                bottom = MaterialTheme.spacing.large
                            ),
                            state = popular,
                            onPresentableClick = onMovieClicked
                        )
                    }
                }
                is ResultState.Search -> {
                    val result = state.result.collectAsLazyPagingItems()

                    if (result.isNotEmpty()) {
                        SearchGridSection(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = MaterialTheme.spacing.medium,
                                start = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.small,
                                bottom = MaterialTheme.spacing.large
                            ),
                            state = result,
                            onSearchResultClick = { id, mediaType ->
                                clearFocus()
                                onResultClicked(id, mediaType)
                            }
                        )
                    } else {
                        SearchEmptyState(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.spacing.medium)
                                .padding(top = MaterialTheme.spacing.extraLarge),
                            onEditButtonClicked = {
                                queryTextFieldFocusRequester.requestFocus()
                            }
                        )
                    }

                }
            }
        }
    }
}