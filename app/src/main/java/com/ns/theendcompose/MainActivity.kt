package com.ns.theendcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.ns.theendcompose.data.model.SnackBarEvent
import com.ns.theendcompose.data.paging.ConfigDataSource
import com.ns.theendcompose.ui.components.others.BottomBar
import com.ns.theendcompose.ui.screens.NavGraphs
import com.ns.theendcompose.ui.screens.destinations.FavoriteScreenDestination
import com.ns.theendcompose.ui.screens.destinations.MovieScreenDestination
import com.ns.theendcompose.ui.screens.destinations.SearchScreenDestination
import com.ns.theendcompose.ui.screens.destinations.TvShowScreenDestination
import com.ns.theendcompose.ui.theme.TheEndComposeTheme
import com.ns.theendcompose.ui.theme.spacing
import com.ns.theendcompose.utils.ImageUrlParser
import com.ns.theendcompose.utils.safeNavigate
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ns.theendcompose.ui.theme.TheEndComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

val LocalImageUrlParser = staticCompositionLocalOf<ImageUrlParser?> { null }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var configDataSource: ConfigDataSource

    @SuppressLint("UnrememberedMutableState")
    @OptIn(
        ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class,
        ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class,
        ExperimentalLifecycleComposeApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel(this)
            val lifeCycleOwner = LocalLifecycleOwner.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val imageUrlParser by mainViewModel.imageUrlParser.collectAsStateWithLifecycle()
            val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
            val snackBarEvent: SnackBarEvent? by mainViewModel.networkSnackBarEvent.collectAsStateWithLifecycle()

            //val useDarkIcons = MaterialTheme.colors.isLight
            val navController = rememberAnimatedNavController()
            val navHostEngine = rememberAnimatedNavHostEngine(
                navHostContentAlignment = Alignment.TopCenter,
                rootDefaultAnimations = RootNavGraphDefaultAnimations(
                    enterTransition = { fadeIn(animationSpec = tween(500)) },
                    exitTransition = { fadeOut(animationSpec = tween(500)) }
                )
            )
            val systemUiController = rememberSystemUiController()
            var currentRoute: String? by rememberSaveable {
                mutableStateOf(null)
            }

            var backQueueRoutes: List<String?> by rememberSaveable {
                mutableStateOf(emptyList())
            }

            navController.apply {
                addOnDestinationChangedListener { controller, _, _ ->
                    currentRoute = controller.currentBackStackEntry?.destination?.route
                    backQueueRoutes = controller.backQueue.map { entry -> entry.destination.route }
                }
                addOnDestinationChangedListener { _, _, _ ->
                    keyboardController?.hide()
                }
            }

            val showBottomBar by derivedStateOf {
                currentRoute in setOf(
                    null,
                    MovieScreenDestination.route,
                    TvShowScreenDestination.route,
                    FavoriteScreenDestination.route,
                    SearchScreenDestination.route
                )
            }

            LaunchedEffect(snackBarEvent) {
                snackBarEvent?.let { event ->
                    snackBarHostState.showSnackbar(
                        message = getString(event.messageStringRes)
                    )
                }
            }
            LaunchedEffect(lifeCycleOwner) {
                lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    Timber.d("Update locale")

                    mainViewModel.updateLocale()
                }
            }

            CompositionLocalProvider(LocalImageUrlParser provides imageUrlParser) {
                TheEndComposeTheme {
                    val navigationBarColor = MaterialTheme.colorScheme.surface
                    val experiment = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                    val checkTheme = isSystemInDarkTheme()
                    SideEffect {
                        if (checkTheme) {
                            systemUiController.setStatusBarColor(
                                color = experiment,
                                darkIcons = false
                            )
                        } else {
                            systemUiController.setStatusBarColor(
                                color = experiment,
                                darkIcons = true
                            )
                        }
                        systemUiController.setNavigationBarColor(
                            color = navigationBarColor,
                            darkIcons = true
                        )
                    }
                    val snackbarHostState = remember { SnackbarHostState() }
                    Scaffold(

                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        bottomBar = {
                            BottomBar(
                                modifier = Modifier.navigationBarsPadding(),
                                currentRoute = currentRoute,
                                backQueueRoutes = backQueueRoutes,
                                visible = showBottomBar
                            ) { route ->
                                navController.safeNavigate(
                                    route = route,
                                    onSameRouteSelected = { sameRoute ->
                                        mainViewModel.onSameRouteSelected(sameRoute)
                                    }
                                )

                            }
                        }
                    ) { innerPadding ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
//                                .padding(innerPadding),
                                .padding(
                                    bottom = if (showBottomBar) {
                                        innerPadding.calculateBottomPadding()
                                    } else MaterialTheme.spacing.small
                                ),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                engine = navHostEngine,
                                navController = navController,
                                dependenciesContainerBuilder = {
                                    dependency(mainViewModel)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}