package com.ns.theendcompose.ui.screens.browse.tvshows

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.ns.theendcompose.ui.screens.destinations.TvShowScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object BrowseTvShowsScreenTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.destination.route) {
            TvShowScreenDestination.route -> slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(300)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return when (initialState.destination.route) {
            TvShowScreenDestination.route -> slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(300)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return when (targetState.destination.route) {
            TvShowScreenDestination.route -> slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Down,
                animationSpec = tween(300)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (targetState.destination.route) {
            TvShowScreenDestination.route -> slideOutOfContainer(
                towards = AnimatedContentScope.SlideDirection.Down,
                animationSpec = tween(300)
            )
            else -> null
        }
    }
}