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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ns.theendcompose.R
import com.ns.theendcompose.data.model.SearchQuery
import com.ns.theendcompose.ui.screens.destinations.ScannerScreenDestination
import com.ns.theendcompose.ui.screens.search.components.QueryTextField
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.CaptureSpeechToText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AnimatedVisibilityScope.SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val onQueryChanged: (query: String) -> Unit = viewModel::onQueryChange
    val onQueryCleared: () -> Unit = viewModel::onQueryClear
    val onAddSearchQuerySuggestions: (SearchQuery) -> Unit = viewModel::addQuerySuggestion

    val onCameraClicked = {
        navigator.navigate(ScannerScreenDestination)
//    }

        val onQuerySuggestionSelected: (String) -> Unit = viewModel::onQuerySuggestionSelected
        SearchScreenContent(
            uiState = uiState,
            onQueryChanged = onQueryChanged,
            onQueryCleared = onQueryCleared,
            onQuerySuggestionSelected = onQuerySuggestionSelected
        )

    }

    @Composable
    fun SearchScreenContent(
        uiState: SearchScreenUIState,
        onQueryChanged: (query: String) -> Unit,
        onQueryCleared: () -> Unit,
//  onResultClicked: (id: Int, type: MediaType) -> Unit,
//  onCameraClicked: () -> Unit = {},
//  onMovieClicked: (Int) -> Unit,
        onQuerySuggestionSelected: (String) -> Unit
    ) {
        val focusManager = LocalFocusManager.current

        val queryTextFieldFocusRequester = remember {
            FocusRequester()
        }

        val clearFocus = { focusManager.clearFocus(force = true) }
        val speechToTextLauncher =
            rememberLauncherForActivityResult(CaptureSpeechToText()) { result ->
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
                query = uiState.query,
                focusRequester = queryTextFieldFocusRequester,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
                    .animateContentSize(),
                suggestions = uiState.suggestions,
                voiceSearchAvailable = uiState.searchOptionsState.voiceSearchAvailable,
                cameraSearchAvailable = uiState.searchOptionsState.cameraSearchAvailable,
                loading = uiState.queryLoading,
                showClearButton = uiState.searchState !is SearchState.EmptyQuery,
                info = {
                    AnimatedVisibility(
                        visible = uiState.searchState is SearchState.InsufficientQuery,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Text(text = stringResource(R.string.search_insufficient_query_length_info_text))
                    }
                },
                onKeyboardSearchClicked = { clearFocus() },
                onQueryClear = {
                    onQueryCleared()
                    queryTextFieldFocusRequester.requestFocus()
                },
                onQueryChange = onQueryChanged,
                onVoiceSearchClick = { speechToTextLauncher.launch(null) },
//                onCameraSearchClick =
            )

        }
    }
}