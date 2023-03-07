package com.ns.theendcompose.ui.screens.details.tvshow

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.ns.theendcompose.R
import com.ns.theendcompose.data.model.*
import com.ns.theendcompose.data.model.tvshow.TvShowDetails
import com.ns.theendcompose.ui.components.button.BackButton
import com.ns.theendcompose.ui.components.button.LikeButton
import com.ns.theendcompose.ui.components.dialogs.ErrorDialog
import com.ns.theendcompose.ui.components.others.AnimatedContentContainer
import com.ns.theendcompose.ui.components.others.DetailsAppBar
import com.ns.theendcompose.ui.components.sections.*
import com.ns.theendcompose.ui.screens.destinations.*
import com.ns.theendcompose.ui.screens.details.components.TvShowDetailsInfoSection
import com.ns.theendcompose.ui.screens.details.components.TvShowDetailsTopContent
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.isNotEmpty
import com.ns.theendcompose.utils.openExternalId
import com.ns.theendcompose.utils.openVideo
import com.ns.theendcompose.utils.shareImdb
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Destination(
    navArgsDelegate = TvShowDetailsScreenArgs::class,
    style = TvShowDetailsScreenTransitions::class
)
@Composable
fun AnimatedVisibilityScope.TvShowDetailsScreen(
    viewModel: TvShowDetailsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = { navigator.navigateUp() }
    val onFavoriteClicked: (details: TvShowDetails) -> Unit = { details ->
        if (uiState.additionalTvShowDetailsInfo.isFavorite) {
            viewModel.onUnlikeClick(details)
        } else {
            viewModel.onLikeClick(details)
        }
    }
    val onCloseClicked: () -> Unit = {
        navigator.popBackStack(uiState.startRoute, inclusive = false)
    }
    val onExternalIdClicked = { id: ExternalId ->
        openExternalId(
            context = context,
            externalId = id
        )
    }
    val onShareClicked = { details: ShareDetails ->
        shareImdb(
            context = context,
            details = details
        )
    }
    val onVideoClicked = { video: Video ->
        openVideo(
            context = context,
            video = video
        )
    }
    val onCreatorClicked = { personId: Int ->
        val destination = PersonDetailsScreenDestination(
            personId = personId,
            startRoute = uiState.startRoute
        )

        navigator.navigate(destination)
    }
    val onTvShowClicked = { tvShowId: Int ->
        val destination =
            TvShowDetailsScreenDestination(
                tvShowId = tvShowId,
                startRoute = uiState.startRoute
            )

        navigator.navigate(destination)
    }
    val onReviewsClicked: () -> Unit = {
        val tvShowId = uiState.tvShowDetails?.id

        if (tvShowId != null) {
            val destination = ReviewsScreenDestination(
                startRoute = uiState.startRoute,
                mediaId = tvShowId,
                type = MediaType.Tv
            )

            navigator.navigate(destination)
        }
    }
    val onSeasonClicked = { seasonNumber: Int ->
        val tvShowId = uiState.tvShowDetails?.id

        if (tvShowId != null) {
            val destination = SeasonDetailsScreenDestination(
                tvShowId = tvShowId,
                seasonNumber = seasonNumber,
                startRoute = uiState.startRoute
            )

            navigator.navigate(destination)
        }
    }
    val onSimilarMoreClicked = {
        val tvShowId = uiState.tvShowDetails?.id

        if (tvShowId != null) {
            val destination = RelatedTvShowScreenDestination(
                tvShowId = tvShowId,
                type = RelationType.Similar,
                startRoute = uiState.startRoute
            )

            navigator.navigate(destination)
        }
    }

    val onRecommendationsMoreClicked = {
        val tvShowId = uiState.tvShowDetails?.id

        if (tvShowId != null) {
            val destination = RelatedTvShowScreenDestination(
                tvShowId = tvShowId,
                type = RelationType.Recommended,
                startRoute = uiState.startRoute
            )

            navigator.navigate(destination)
        }
    }
    TvShowDetailsScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onExternalIdClicked = onExternalIdClicked,
        onShareClicked = onShareClicked,
        onVideoClicked = onVideoClicked,
        onFavoriteClicked = onFavoriteClicked,
        onCloseClicked = onCloseClicked,
        onCreatorClicked = onCreatorClicked,
        onTvShowClicked = onTvShowClicked,
        onSeasonClicked = onSeasonClicked,
        onSimilarMoreClicked = onSimilarMoreClicked,
        onRecommendationsMoreClicked = onRecommendationsMoreClicked,
        onReviewsClicked = onReviewsClicked
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TvShowDetailsScreenContent(
    uiState: TvShowDetailsScreenUIState,
    onBackClicked: () -> Unit,
    onExternalIdClicked: (id: ExternalId) -> Unit,
    onShareClicked: (details: ShareDetails) -> Unit,
    onVideoClicked: (video: Video) -> Unit,
    onFavoriteClicked: (details: TvShowDetails) -> Unit,
    onCloseClicked: () -> Unit,
    onCreatorClicked: (personId: Int) -> Unit,
    onTvShowClicked: (tvShowId: Int) -> Unit,
    onSeasonClicked: (seasonNumber: Int) -> Unit,
    onSimilarMoreClicked: () -> Unit,
    onRecommendationsMoreClicked: () -> Unit,
    onReviewsClicked: () -> Unit
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val similar = uiState.associatedTvShow.similar.collectAsLazyPagingItems()
    val recommendations = uiState.associatedTvShow.recommendations.collectAsLazyPagingItems()

    val scrollState = rememberScrollState()
    val scrollToStart = {
        coroutineScope.launch {
            scrollState.animateScrollTo(0)
        }
    }
    val imdbExternalId by derivedStateOf {
        uiState.associatedContent.externalIds?.filterIsInstance<ExternalId.Imdb>()?.firstOrNull()
    }

    var showErrorDialog by remember { mutableStateOf(false) }

    var topSectionHeight: Float? by remember { mutableStateOf(null) }
    val appbarHeight = density.run { 56.dp.toPx() }
    val topSectionScrollLimitValue: Float? = topSectionHeight?.minus(appbarHeight)

    LaunchedEffect(uiState.error) {
        showErrorDialog = uiState.error != null
    }

    BackHandler(showErrorDialog) {
        showErrorDialog = false
    }

    if (showErrorDialog) {
        ErrorDialog(
            onDismissRequest = {
                showErrorDialog = false
            },
            onConfirmClick = {
                showErrorDialog = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            PresentableDetailsTopSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        topSectionHeight = coordinates.size.height.toFloat()
                    },
                presentable = uiState.tvShowDetails,
                backdrops = uiState.associatedContent.backdrops,
                scrollState = scrollState,
                scrollValueLimit = topSectionScrollLimitValue
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(MaterialTheme.spacing.small)
                ) {
                    TvShowDetailsTopContent(
                        modifier = Modifier.fillMaxWidth(),
                        tvShowDetails = uiState.tvShowDetails
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Crossfade(
                    modifier = Modifier.fillMaxWidth(),
                    targetState = uiState.associatedContent.externalIds
                ) { ids ->
                    if (ids != null) {
                        ExternalIdsSection(
                            modifier = Modifier.fillMaxWidth(),
                            externalIds = ids,
                            onExternalIdClick = onExternalIdClicked
                        )
                    }
                }
            }
            TvShowDetailsInfoSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .animateContentSize(),
                tvShowDetails = uiState.tvShowDetails,
                nextEpisodeDaysRemaining = uiState.additionalTvShowDetailsInfo.nextEpisodeRemainingDays,
                imdbExternalId = imdbExternalId,
                onShareClicked = onShareClicked
            )
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.additionalTvShowDetailsInfo.watchProviders != null
            ) {
                if (uiState.additionalTvShowDetailsInfo.watchProviders != null) {
                    WatchProvidersSection(
                        modifier = Modifier.fillMaxWidth(),
                        watchProviders = uiState.additionalTvShowDetailsInfo.watchProviders,
                        title = stringResource(R.string.available_at)
                    )
                }
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.tvShowDetails?.creators.isNullOrEmpty()
            ){
                MemberSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.tv_series_details_creators),
                    members = uiState.tvShowDetails?.creators ?: emptyList(),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
                    onMemberClick = onCreatorClicked
                )
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.tvShowDetails?.seasons.isNullOrEmpty()
            ){
                SeasonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(vertical = MaterialTheme.spacing.small),
                    title = stringResource(R.string.tv_series_details_seasons),
                    seasons = uiState.tvShowDetails?.seasons ?: emptyList(),
                    onSeasonClick = onSeasonClicked
                )
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = recommendations.isNotEmpty()
            ) {
                PresentableSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.tv_series_details_recommendations),
                    state = recommendations,
                    showLoadingAtRefresh = false,
                    onMoreClick = onRecommendationsMoreClicked,
                    onPresentableClick = { tvShowId ->
                        if (tvShowId != uiState.tvShowDetails?.id) {
                            onTvShowClicked(tvShowId)
                        } else {
                            scrollToStart()
                        }
                    }
                )
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = similar.isNotEmpty()
            ) {
                PresentableSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.tv_series_details_similar),
                    state = similar,
                    showLoadingAtRefresh = false,
                    onMoreClick = onSimilarMoreClicked,
                    onPresentableClick = { tvShowId ->
                        if (tvShowId != uiState.tvShowDetails?.id) {
                            onTvShowClicked(tvShowId)
                        } else {
                            scrollToStart()
                        }
                    }
                )
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.associatedContent.videos.isNullOrEmpty()
            ) {
                VideosSection(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.tv_series_details_videos),
                    videos = uiState.associatedContent.videos ?: emptyList(),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium),
                    onVideoClicked = onVideoClicked
                )
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = uiState.additionalTvShowDetailsInfo.reviewsCount > 0
            ) {
                ReviewSection(
                    modifier = Modifier.fillMaxWidth(),
                    count = uiState.additionalTvShowDetailsInfo.reviewsCount,
                    onClick = onReviewsClicked
                )
            }
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    insets = WindowInsets(bottom = MaterialTheme.spacing.medium)
                )
            )
        }
        DetailsAppBar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = null,
            backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f),
            scrollState = scrollState,
            transparentScrollValueLimit = topSectionScrollLimitValue,
            action = {
                BackButton(
                    onBackClicked
                )
            },
            trailing = {
                LikeButton(
                    isFavourite = uiState.additionalTvShowDetailsInfo.isFavorite,
                    onClick = {
                        val details = uiState.tvShowDetails

                        if (details != null) {
                            onFavoriteClicked(details)
                        }
                    }
                )
            }
        )
    }
}