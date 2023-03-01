package com.ns.theendcompose.ui.screens.favorite

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.ns.theendcompose.data.model.FavoriteType
import com.ns.theendcompose.ui.components.others.FavoriteEmptyState
import com.ns.theendcompose.ui.components.sections.PresentableGridSection
import com.ns.theendcompose.ui.components.selectors.FavoriteTypeSelector
import com.ns.theendcompose.ui.screens.destinations.*
import com.ns.theendcompose.ui.screens.favorite.FavoritesScreenViewModel
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.isNotEmpty
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalLifecycleComposeApi::class)
@Destination
@Composable
fun AnimatedVisibilityScope.FavoriteScreen(
    viewModel: FavoritesScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onFavoriteTypeSelected: (type: FavoriteType) -> Unit = viewModel::onFavoriteTypeSelected
    val onFavoriteClicked: (favoriteId: Int) -> Unit = { id ->
        val destination = when (uiState.selectedFavoriteType) {
            FavoriteType.Movie -> {
                MovieDetailsScreenDestination(
                    movieId = id,
                    startRoute = FavoriteScreenDestination.route
                )
            }

            FavoriteType.TvShow -> {
                TvShowDetailsScreenDestination(
                    tvShowId = id,
                    startRoute = FavoriteScreenDestination.route
                )
            }
        }
        navigator.navigate(destination)
    }
    val onNavigateToMoviesButtonClicked: () -> Unit = {
        navigator.navigate(MovieScreenDestination) {
            popUpTo(MovieDetailsScreenDestination.route) {
                inclusive = true
            }
        }
    }
    val onNavigateToTvShowButtonClicked: () -> Unit = {
        navigator.navigate(TvShowScreenDestination) {
            popUpTo(MovieScreenDestination.route) {
                inclusive = true
            }
        }
    }
    FavoriteScreenContent(
        uiState = uiState,
        onFavoriteTypeSelected = onFavoriteTypeSelected,
        onFavoriteClicked = onFavoriteClicked,
        onNavigateToMoviesButtonClicked = onNavigateToMoviesButtonClicked,
        onNavigateToTvShowButtonClicked = onNavigateToTvShowButtonClicked
    )
}

@Composable
fun FavoriteScreenContent(
    uiState: FavoritesScreenUIState,
    onFavoriteTypeSelected: (type: FavoriteType) -> Unit,
    onFavoriteClicked: (favouriteId: Int) -> Unit,
    onNavigateToMoviesButtonClicked: () -> Unit,
    onNavigateToTvShowButtonClicked: () -> Unit
) {
    val favoritesLazyItems = uiState.favorites.collectAsLazyPagingItems()
    val notEmpty = favoritesLazyItems.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        FavoriteTypeSelector(
            selected = uiState.selectedFavoriteType,
            onSelected = onFavoriteTypeSelected
        )
        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = notEmpty
        ) { notEmpty ->
            if (notEmpty) {
                PresentableGridSection(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = MaterialTheme.spacing.medium,
                        start = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.large
                    ),
                    state = favoritesLazyItems,
                    showRefreshLoading = false,
                    onPresentableClick = onFavoriteClicked
                )
            } else {
                FavoriteEmptyState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.spacing.medium)
                        .padding(top = MaterialTheme.spacing.extraLarge),
                    type = uiState.selectedFavoriteType,
                    onButtonClick = when (uiState.selectedFavoriteType) {
                        FavoriteType.Movie -> onNavigateToMoviesButtonClicked
                        FavoriteType.TvShow -> onNavigateToTvShowButtonClicked
                    }
                )
            }
        }
    }
}