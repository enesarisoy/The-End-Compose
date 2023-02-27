package com.ns.theendcompose.ui.screens.search

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.ns.theendcompose.data.model.SearchQuery
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
    val speechToTextLauncher = rememberLauncherForActivityResult(CaptureSpeechToText()) { result ->
        if (result != null) {
            focusManager.clearFocus()
            onQueryChanged(result)
        }
    }
}