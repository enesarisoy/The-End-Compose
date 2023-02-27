package com.ns.theendcompose.ui.screens.tvshow

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ns.theendcompose.MainViewModel
import com.ns.theendcompose.R
import com.ns.theendcompose.ui.components.sections.PresentableSection
import com.ns.theendcompose.ui.components.sections.PresentableTopSection
import com.ns.theendcompose.ui.screens.destinations.TvShowScreenDestination
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.isAnyRefreshing
import com.ns.theendcompose.utils.refreshAll
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun AnimatedVisibilityScope.TvShowScreen(
    mainViewModel: MainViewModel,
    viewModel: TvShowScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        mainViewModel.sameBottomRoute.collectLatest { sameRoute ->
            if (sameRoute == TvShowScreenDestination.route)
                scrollState.animateScrollTo(0)
        }
    }

    TvShowScreenContent(
        uiState = uiState,
        scrollState = scrollState
    ) {

    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TvShowScreenContent(
    uiState: TvShowScreenUIState,
    scrollState: ScrollState,
    onDiscoverTvShowsClicked: () -> Unit
) {
    val density = LocalDensity.current

    val topRatedLazyItems = uiState.tvShowsState.topRated.collectAsLazyPagingItems()
    val discoverLazyItems = uiState.tvShowsState.discover.collectAsLazyPagingItems()
    val onTheAirLazyItems = uiState.tvShowsState.onTheAir.collectAsLazyPagingItems()
    val trendingLazyItems = uiState.tvShowsState.trending.collectAsLazyPagingItems()
    val airingTodayLazyItems = uiState.tvShowsState.airingToday.collectAsLazyPagingItems()

    var topSectionHeight: Float? by remember {
        mutableStateOf(null)
    }
    val appBarHeight = density.run { 56.dp.toPx() }
    val topSectionScrollLimitValue: Float? = topSectionHeight?.minus(appBarHeight)

    val isRefreshing by derivedStateOf {
        listOf(
            topRatedLazyItems,
            discoverLazyItems,
            onTheAirLazyItems,
            trendingLazyItems,
            airingTodayLazyItems
        ).isAnyRefreshing()
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    val refreshAllPagingData = {
        listOf(
            topRatedLazyItems,
            discoverLazyItems,
            onTheAirLazyItems,
            trendingLazyItems,
            airingTodayLazyItems
        ).refreshAll()
    }

    LaunchedEffect(isRefreshing) {
        swipeRefreshState.isRefreshing = isRefreshing
    }

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = swipeRefreshState,
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = MaterialTheme.spacing.large),
                state = state,
                refreshTriggerDistance = trigger,
                fade = true,
                scale = true,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            )
        },
        onRefresh = refreshAllPagingData
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            PresentableTopSection(
                title = stringResource(id = R.string.now_airing_tv_series),
                state = onTheAirLazyItems,
                scrollState = scrollState,
                scrollValueLimit = topSectionScrollLimitValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        topSectionHeight = coordinates.size.height.toFloat()
                    }
            )

            PresentableSection(
                title = stringResource(id = R.string.explore_tv_series),
                state = discoverLazyItems
            )
            PresentableSection(
                title = stringResource(id = R.string.top_rated_tv_series),
                state = topRatedLazyItems
            )
            PresentableSection(
                title = stringResource(id = R.string.trending_tv_series),
                state = trendingLazyItems
            )
            PresentableSection(
                title = stringResource(id = R.string.today_airing_tv_series),
                state = airingTodayLazyItems
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
    }
}

