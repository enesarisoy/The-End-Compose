package com.ns.theendcompose.ui.screens.details.person

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ns.theendcompose.R
import com.ns.theendcompose.data.model.ExternalId
import com.ns.theendcompose.data.model.MediaType
import com.ns.theendcompose.ui.components.dialogs.ErrorDialog
import com.ns.theendcompose.ui.components.others.AnimatedContentContainer
import com.ns.theendcompose.ui.components.others.BasicAppBar
import com.ns.theendcompose.ui.components.sections.ExternalIdsSection
import com.ns.theendcompose.ui.screens.destinations.MovieDetailsScreenDestination
import com.ns.theendcompose.ui.screens.destinations.TvShowDetailsScreenDestination
import com.ns.theendcompose.ui.screens.details.components.CreditsList
import com.ns.theendcompose.ui.screens.details.components.PersonDetailsInfoSection
import com.ns.theendcompose.ui.screens.details.components.PersonDetailsTopContent
import com.ns.theendcompose.ui.screens.details.components.PersonProfileImage
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.openExternalId
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@OptIn(ExperimentalLifecycleComposeApi::class)
@Destination(
    navArgsDelegate = PersonDetailsScreenArgs::class,
    style = PersonDetailsScreenTransitions::class
)
@Composable
fun AnimatedVisibilityScope.PersonDetailsScreen(
    viewModel: PersonDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = { navigator.navigateUp() }
    val onCloseClicked: () -> Unit = {
        navigator.popBackStack(uiState.startRoute, inclusive = false)
    }
    val onExternalIdClicked = { id: ExternalId ->
        openExternalId(
            context = context,
            externalId = id
        )
    }
    val onMediaClicked = { mediaType: MediaType, id: Int ->
        val destination = when (mediaType) {
            MediaType.Movie -> {
                MovieDetailsScreenDestination(
                    movieId = id,
                    startRoute = uiState.startRoute
                )
            }

            MediaType.Tv -> {
                TvShowDetailsScreenDestination(
                    tvShowId = id,
                    startRoute = uiState.startRoute
                )
            }

            else -> null
        }

        if (destination != null) {
            navigator.navigate(destination)
        }
    }
    PersonDetailsScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onCloseClicked = onCloseClicked,
        onExternalIdClicked = onExternalIdClicked,
        onMediaClicked = onMediaClicked
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun PersonDetailsScreenContent(
    uiState: PersonDetailsScreenUIState,
    onBackClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    onExternalIdClicked: (id: ExternalId) -> Unit,
    onMediaClicked: (type: MediaType, id: Int) -> Unit
) {
    val personDetailsState by derivedStateOf {
        uiState.details?.let { PersonDetailsState.Result(it) }
            ?: PersonDetailsState.Loading
    }
    var showErrorDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(uiState.error) {
        showErrorDialog = uiState.error != null
        Timber.tag("TAG").e("PersonDetailsScreenContent: ")
    }
    BackHandler(showErrorDialog) {
        showErrorDialog = false
    }

    if (showErrorDialog) {
        ErrorDialog(onDismissRequest = {
            showErrorDialog = false
        }, onConfirmClick = {
            showErrorDialog = false
        })
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 56.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                val (profileImageRef, contentRef) = createRefs()
                PersonProfileImage(
                    modifier = Modifier.constrainAs(profileImageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                    personDetailsState = personDetailsState
                )
                Column(
                    modifier = Modifier
                        .constrainAs(contentRef) {
                            start.linkTo(profileImageRef.end)
                            end.linkTo(parent.end)
                            top.linkTo(profileImageRef.top)
                            bottom.linkTo(profileImageRef.bottom)

                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                        .padding(start = MaterialTheme.spacing.medium)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(MaterialTheme.spacing.small)
                    ) {
                        PersonDetailsTopContent(
                            modifier = Modifier.fillMaxWidth(),
                            personDetails = uiState.details
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    uiState.externalIds?.let { ids ->
                        ExternalIdsSection(
                            modifier = Modifier.fillMaxWidth(),
                            externalIds = ids,
                            onExternalIdClick = onExternalIdClicked
                        )
                    }
                }
            }
            PersonDetailsInfoSection(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                personDetails = uiState.details
            )
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.credits?.cast.isNullOrEmpty()
            ) {
                CreditsList(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.person_details_screen_cast),
                    credits = uiState.credits?.cast ?: emptyList(),
                    onCreditsClick = onMediaClicked
                )
            }
            AnimatedContentContainer(
                modifier = Modifier.fillMaxWidth(),
                visible = !uiState.credits?.crew.isNullOrEmpty()
            ) {
                CreditsList(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.person_details_screen_crew),
                    credits = uiState.credits?.crew ?: emptyList(),
                    onCreditsClick = onMediaClicked
                )
            }

            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    insets = WindowInsets(bottom = MaterialTheme.spacing.medium)
                )
            )
        }
        BasicAppBar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = stringResource(R.string.person_details_screen_appbar_label),
//            backgroundColor = Color.Black.copy(0.7f),
            action = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "go back",
                    )
                }
            },
        )
    }
}